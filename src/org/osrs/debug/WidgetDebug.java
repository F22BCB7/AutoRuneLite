package org.osrs.debug;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;

import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import org.osrs.api.methods.MethodContext;
import org.osrs.api.objects.RSInterface;
import org.osrs.api.objects.RSWidget;
import org.osrs.api.wrappers.IntegerNode;
import org.osrs.api.wrappers.Node;
import org.osrs.injection.FieldHook;
import org.osrs.util.Data;

public class WidgetDebug {
	public WidgetDebugger debugger = null;
	private RSWidget widgetToDisplay = null;
	private MethodContext methods = null;
	private JPanel paintPanel = null;
	public BufferedImage paintPanelImage = null;
	private int currentDataIndex=0;
	public WidgetDebug(MethodContext methods){
		this.methods=methods;
		this.paintPanelImage = new BufferedImage(400, 500, BufferedImage.TYPE_INT_RGB);
		this.debugger=new WidgetDebugger();
		this.debugger.init();
	}
	public Graphics paint(Graphics g){
		try {
			if(!Data.clientFrame.widgetDebugOption.getState()){
				if(debugger.isVisible())
					debugger.setVisible(false);
				return g;
			}
			if(widgetToDisplay!=null){
				g.setColor(Color.CYAN);
				g.drawRect(widgetToDisplay.getAbsoluteX(), widgetToDisplay.getAbsoluteY(), widgetToDisplay.width(), widgetToDisplay.height());
			}

			if(paintPanelImage==null || paintPanelImage.getWidth()!=paintPanel.getWidth() || paintPanelImage.getHeight()!=paintPanel.getHeight())
				paintPanelImage = new BufferedImage(paintPanel.getWidth(), paintPanel.getHeight(), BufferedImage.TYPE_INT_RGB);
			Graphics g2 = paintPanelImage.getGraphics();
			g2.setColor(Color.BLACK);
			g2.fillRect(0, 0, paintPanel.getWidth(), paintPanel.getHeight());
			g2.setColor(Color.YELLOW);
			int x = 15;
			int y = 15;
			if(widgetToDisplay!=null){
				int maxCount=(paintPanel.getHeight()-15)/15;
				int newIndex=currentDataIndex;
				if(newIndex+maxCount>=dataNames.length)
					newIndex = dataNames.length - maxCount;
				if(newIndex<0)
					newIndex=0;
				currentDataIndex=newIndex;
				for(int i=0; (i<maxCount) && (i+currentDataIndex<dataNames.length); ++i){
					Class<?> rswidgetClazz = widgetToDisplay.getClass();
					Method m = rswidgetClazz.getMethod(dataNames[i+currentDataIndex], new Class<?>[]{});
					if(m!=null){
						Object data = m.invoke(widgetToDisplay, new Object[]{});
						if(data!=null && data.getClass().isArray()){
							Object[] array = new Object[Array.getLength(data)];
							for(int k=0;k<array.length;++k){
								array[k]=Array.get(data, k);
							}
							g2.drawString(dataNames[i+currentDataIndex]+" : "+Arrays.toString(array), x, y);
						}
						else{
							if(dataNames[i+currentDataIndex].equals("visibleCycle")){
								FieldHook fh = Data.clientModscript.resolver.getFieldHook("Widget", "visibleCycle", false);
								int clientCycle = methods.game.client().widgetVisibleCycle() * (int)Data.clientModscript.getGetterMultiplier("Widget", "visibleCycle", false);
								g2.drawString(dataNames[i+currentDataIndex]+" : "+data+" : client:"+(clientCycle)+" : "+(((int)data+1)>=clientCycle), x, y);
							}
							else if(dataNames[i+currentDataIndex].equals("displayCycle"))
								g2.drawString(dataNames[i+currentDataIndex]+" : "+data+" : client:"+methods.game.client().gameCycle()+" : "+(methods.game.client().gameCycle()==(int)data), x, y);
							else if(dataNames[i+currentDataIndex].equals("relativeX"))
								g2.drawString(dataNames[i+currentDataIndex]+" : "+data+" : absoluteX:"+widgetToDisplay.getAbsoluteX(), x, y);
							else if(dataNames[i+currentDataIndex].equals("relativeY"))
								g2.drawString(dataNames[i+currentDataIndex]+" : "+data+" : absoluteY:"+widgetToDisplay.getAbsoluteY(), x, y);
							else if(dataNames[i+currentDataIndex].equals("widgetUID")){
								Node node = methods.game.widgetFlags().invoke_get(((long)widgetToDisplay.id()<<32)+(long)widgetToDisplay.index());
								g2.drawString(dataNames[i+currentDataIndex]+" : "+data+" : widgetFlagUID:"+(((long)widgetToDisplay.id()<<32)+(long)widgetToDisplay.index())+" : widgetFlagValue"+(node!=null?(((IntegerNode)node).value()+" : "+((((IntegerNode)node).value() >> 21 & 1))):"null"), x, y);
							}
							else
								g2.drawString(dataNames[i+currentDataIndex]+" : "+data, x, y);
						}
						y+=15;
					}
				}
			}
			else{
				g2.drawString("Please select a widget to display data!", x, y);
				y+=15;
			}
			debugger.repaint();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return g;
	}
	public class WidgetDebugger extends javax.swing.JFrame implements MouseWheelListener{
		private static final long serialVersionUID = 1L;
		public WidgetDebugger(){}
		public void init(){
			setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
			addWindowListener(new WindowAdapter() {
			    public void windowClosing(WindowEvent e) {
			        Data.clientFrame.widgetDebugOption.setState(false);
			        currentDataIndex=0;
			        widgetToDisplay=null;
			    }
			});
			paintPanel = new PaintPanel();
			widgetDebuggerLabel = new javax.swing.JLabel();
			widgetDebuggerLabel.setFont(new java.awt.Font("Trebuchet MS", 1, 14));
			widgetDebuggerLabel.setText("Widget Debugger");

			jButton3 = new javax.swing.JButton();
			jButton3.setText("Refresh");
			refreshListener = new RefreshListener();
			jButton3.addActionListener(refreshListener);

			jButton4 = new javax.swing.JButton();
			jButton4.setText("Goto Parent");
			jButton4.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					if(widgetToDisplay!=null){
						int parentID = widgetToDisplay.getParentID();
						if(parentID!=-1){
							RSWidget parent = methods.widgets.getChild(parentID);
							if(parent!=null){
								DefaultMutableTreeNode node = null;
						        Enumeration<?> e = root.breadthFirstEnumeration();
						        while (e.hasMoreElements())
						        { 
						        	node = (DefaultMutableTreeNode) e.nextElement();            
						        	if(node==null)
						        		continue;
						        	if(node instanceof DataNode){
						        		DataNode dn = (DataNode)node;
						        		if(dn.currentWidget!=null && dn.currentWidget.id()==parent.id()){
						        			TreePath path = new TreePath(node.getPath());
						        			jTree1.setSelectionPath(path);
						        			
						        		}
						        	}
						        }
							}
						}
					}
				}
			});
			jButton1 = new javax.swing.JButton();
			jButton1.setText("Change View");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					viewType = viewType==0?1:0;
					refreshListener.actionPerformed(null);//refresh list
				}
			});
			searchByLabel = new JLabel("Data Search");
			jComboBox = new JComboBox<String>();
			for(String s : dataNames)
				jComboBox.addItem(s);
			jComboBox.setEditable(false);
			valueSearchTerm = new JTextField();
			jButton2 = new javax.swing.JButton();
			searchResultList = new JList();
		    DefaultListModel listModel = new DefaultListModel();
			listModel.addElement("Empty Search Results");
			searchResultList.setModel(listModel);
			searchResultList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			searchResultList.addListSelectionListener(new ListSelectionListener(){
				@Override
				public void valueChanged(ListSelectionEvent arg0) {
					Object value = searchResultList.getSelectedValue();
					if(value!=null && value instanceof SearchResultNode){
						SearchResultNode result = (SearchResultNode)value;
						if(result.linkedNode!=null){
		        			TreePath path = new TreePath(result.linkedNode.getPath());
		        			jTree1.setSelectionPath(path);
						}
					}
				}
			});
			jScrollPane2 = new javax.swing.JScrollPane();
			jScrollPane2.setViewportView(searchResultList);
			jButton2.setText("Search");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					try{
						((DefaultListModel)searchResultList.getModel()).clear();
						DefaultMutableTreeNode node = null;
				        Enumeration<?> e = root.breadthFirstEnumeration();
				        while (e.hasMoreElements())
				        { 
				        	node = (DefaultMutableTreeNode) e.nextElement();            
				        	if(node==null)
				        		continue;
				        	if(node instanceof DataNode){
				        		DataNode dn = (DataNode)node;
				        		if(dn.currentWidget!=null){
					        		Class<?> rswidgetClazz = RSWidget.class;
									Method m = rswidgetClazz.getMethod(""+jComboBox.getSelectedItem(), new Class<?>[]{});
									if(m!=null){
										Object data = m.invoke(dn.currentWidget, new Object[]{});
										if(data!=null){
											if(data.getClass().isArray()){
												int length = Array.getLength(data);
												for(int i=0;i<length;++i){
													Object data2 = Array.get(data, i);
													if(data2!=null){
														if(data2 instanceof String){
															if(((String)data2).contains(valueSearchTerm.getText())){
																SearchResultNode newResult = new SearchResultNode(dn);
																((DefaultListModel)searchResultList.getModel()).addElement(newResult);
															}
														}
														else if(data2.toString().equals(valueSearchTerm.getText())){
															SearchResultNode newResult = new SearchResultNode(dn);
															((DefaultListModel)searchResultList.getModel()).addElement(newResult);
															break;
														}
													}
												}
											}
											else if(data instanceof String){
												if(((String)data).contains(valueSearchTerm.getText())){
													SearchResultNode newResult = new SearchResultNode(dn);
													((DefaultListModel)searchResultList.getModel()).addElement(newResult);
												}
											}
											else if(data.toString().equals(valueSearchTerm.getText())){
												SearchResultNode newResult = new SearchResultNode(dn);
												((DefaultListModel)searchResultList.getModel()).addElement(newResult);
											}
										}
									}
				        		}
				        	}
				        }
					}
					catch(Exception e){
						e.printStackTrace();
					}
				}
			});
			
			root = new DataNode("Runescape Widgets");
			treeModel = new DefaultTreeModel(root);
			createClassNodes();
			jTree1 = new javax.swing.JTree(root);
			jTree1.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
			jTree1.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
				public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
					if(jTree1.getSelectionPath()!=null){
						DataNode selected = (DataNode) jTree1.getSelectionPath().getLastPathComponent();
						widgetToDisplay = selected.currentWidget;
					}
				}
			});
			jScrollPane1 = new javax.swing.JScrollPane();
			jScrollPane1.setViewportView(jTree1);
			javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
			getContentPane().setLayout(layout);
			layout.setHorizontalGroup(
					layout.createSequentialGroup()
					.addGap(10)
					.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(layout.createSequentialGroup()
								.addGap(5)
								.addComponent(widgetDebuggerLabel)
								.addGap(10)
								.addComponent(jButton3))
						.addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 210, 210)
						)
					.addGap(10)
					.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
							.addGroup(layout.createSequentialGroup()
								.addComponent(jButton4)
								.addGap(10)
								.addComponent(jButton1))
							.addComponent(paintPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
							)
					.addGap(18)
					.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
							.addComponent(searchByLabel)
							.addComponent(jComboBox, javax.swing.GroupLayout.DEFAULT_SIZE, 150, 150)
							.addComponent(valueSearchTerm, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
							.addComponent(jButton2)
							.addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
							)
					.addGap(18)
			);
			layout.setVerticalGroup(
					layout.createSequentialGroup()
					.addGap(10)
					.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
							.addGroup(layout.createSequentialGroup()
									.addGap(5)
									.addComponent(widgetDebuggerLabel))
							.addComponent(jButton3)
							.addComponent(jButton4)
							.addComponent(jButton1)
							.addGroup(layout.createSequentialGroup()
									.addGap(5)
									.addComponent(searchByLabel))
					)
					.addGap(10)
					.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
							.addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
							.addComponent(paintPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
							.addGroup(layout.createSequentialGroup()
									.addComponent(jComboBox, javax.swing.GroupLayout.DEFAULT_SIZE, 30, 30)
									.addGap(10)
									.addComponent(valueSearchTerm, javax.swing.GroupLayout.DEFAULT_SIZE, 30, 30)
									.addGap(10)
									.addComponent(jButton2)
									.addGap(10)
									.addComponent(jScrollPane2)
									.addGap(10)
							)
					)
					.addGap(10)
					);
			paintPanel.addMouseWheelListener(this);
			pack();
		}
		@Override
		public void setSize(int width, int height){
			int widthDiff = -(this.getWidth()-width);
			super.setSize(width, height);
			paintPanel.setSize(paintPanel.getWidth()+widthDiff, height);
			paintPanelImage = new BufferedImage(paintPanel.getWidth(), paintPanel.getHeight(), BufferedImage.TYPE_INT_RGB);
		}
		public int viewType = 0;
		private RefreshListener refreshListener;
		private javax.swing.JScrollPane jScrollPane2;
		private javax.swing.JList<SearchResultNode> searchResultList;
		private javax.swing.JTextField valueSearchTerm;                                 
		private javax.swing.JButton jButton1;                                             
		private javax.swing.JButton jButton2;                           
		private javax.swing.JButton jButton3;
		private javax.swing.JButton jButton4;
		private javax.swing.JLabel widgetDebuggerLabel;
		private javax.swing.JLabel searchByLabel;
		private javax.swing.JScrollPane jScrollPane1;
		private javax.swing.JTree jTree1;                                    
		private javax.swing.JComboBox<String> jComboBox;
		@Override
		public void mouseWheelMoved(MouseWheelEvent arg0) {
			int scroll = arg0.getUnitsToScroll();
			int newIndex=currentDataIndex;
			newIndex+=scroll;
			if(newIndex>=dataNames.length)
				newIndex = dataNames.length - ((paintPanel.getHeight()-15)/15);
			if(newIndex<0)
				newIndex=0;
			currentDataIndex=newIndex;
		}
		private class PaintPanel extends JPanel{
			@Override
			public void paint(Graphics g){
				g.drawImage(paintPanelImage, 0, 0, null);
			}
		}
	}
	private class RefreshListener implements java.awt.event.ActionListener{
		@Override
		public void actionPerformed(java.awt.event.ActionEvent evt) {
			currentDataIndex=0;
			debugger.jTree1.clearSelection();
			if(debugger.viewType==0){
				root = new DataNode("Data Structure View");
				root.setAllowsChildren(true);
				for(RSInterface i : methods.widgets.getAll()){
					if(i!=null){
						DataNode n = new DataNode(""+i.index, i);
						boolean add=false;
						for(RSWidget ic : i.getChildren()){
							if(ic!=null){// && ic.isDisplayed()){
								DataNode n2 = new DataNode(""+ic.getIndex(), ic);
								for(RSWidget ic2 : ic.getChildren()){
									if(ic2!=null)// && ic2.isDisplayed())
										n2.addChild(new DataNode(""+ic2.getIndex(), ic2));
								}
								add=true;
								n.addChild(n2);
							}
						}
						if(add)
							root.addChild(n);
					}
				}
			}
			else if(debugger.viewType==1){
				root = new DataNode("Relationship Structure View");
				root.setAllowsChildren(true);
				HashMap<Integer, DataNode> relationshipMap = new HashMap<Integer, DataNode>();
				for(RSInterface i : methods.widgets.getAll()){
					if(i!=null){
						for(RSWidget ic : i.getChildren()){
							if(ic!=null){
								relationshipMap.put(ic.id(), new DataNode(""+ic.index(), ic));
								for(RSWidget ic2 : ic.getChildren()){
									if(ic2!=null){
										relationshipMap.put(ic2.id(), new DataNode(""+ic2.index(), ic2));
									}
								}
							}
						}
					}
				}
				for(int id : relationshipMap.keySet()){
					DataNode node = relationshipMap.get(id);
					if(node==null || node.currentWidget==null)
						continue;
					DataNode parent = relationshipMap.get(node.currentWidget.parentID());
					if(parent==null){
						addChildren(node, relationshipMap);
						root.addChild(node);
					}
				}
			}
			if(root.getChildCount()<1)
				root.addChild(new DataNode("None Found"));
			treeModel = new DefaultTreeModel(root);
			debugger.jTree1.setModel(treeModel);
			treeModel.reload();
			debugger.jTree1.repaint();
			widgetToDisplay=null;
		}
	}
	private void addChildren(DataNode node, HashMap<Integer, DataNode> relationshipMap){
		if(node==null || node.currentWidget==null)
			return;
		for(DataNode node2 : relationshipMap.values()){
			if(node2==null || node2.currentWidget==null)
				continue;
			if(node2.currentWidget.parentID()==node.currentWidget.id()){
				addChildren(node2, relationshipMap);
				node.addChild(node2);
			}
		}
	}
	private class SearchResultNode{
		public DataNode linkedNode;
		public SearchResultNode(DataNode data){
			linkedNode = data;
		}
		@Override
		public String toString(){
			String s = "";
			if(linkedNode!=null){
				if(linkedNode.currentWidget!=null){
					s = ""+linkedNode.currentWidget.getIndex();
					RSWidget parent = linkedNode.currentWidget.getParentWidget();
					if(parent!=null){
						s = parent.getIndex()+":"+s;
						RSInterface parentIface = parent.getParentInterface();
						if(parentIface!=null)
							s = parentIface.index+":"+s;
					}
					else{
						RSInterface parentIface = linkedNode.currentWidget.getParentInterface();
						if(parentIface!=null)
							s = parentIface.index+":"+s;
					}
				}
			}
			return s;
		}
	}
	private DataNode root;
	private DefaultTreeModel treeModel;
	private class DataNode extends DefaultMutableTreeNode {
		private static final long serialVersionUID = 1L;
		private boolean areChildrenDefined = false;
		private int numChildren;
		public ArrayList<DataNode> childrenArray = new ArrayList<DataNode>();
		public String currentString;//Current name of class/field/method

		public RSInterface currentInterface;
		public RSWidget currentWidget;

		public DataNode(String s) {
			super(s);
			this.setAllowsChildren(true);
			numChildren = 0;
			currentString=s;
		}
		public DataNode(String s, RSInterface i){
			super(s);
			this.setAllowsChildren(true);
			numChildren=0;
			currentString=s;
			currentInterface=i;
		}
		public DataNode(String s, RSWidget i){
			super(s);
			this.setAllowsChildren(true);
			numChildren=0;
			currentString=s;
			currentWidget=i;
		}
		@Override
		public boolean isLeaf() {
			return (!areChildrenDefined);
		}
		@Override
		public int getChildCount() {
			if (!areChildrenDefined)
				return 0;
			return numChildren;
		}
		public void addChild(DataNode d){
			areChildrenDefined=true;
			add(d);
			childrenArray.add(d);
			numChildren++;	
		}
		@Override
		public String toString() {
			if(debugger.viewType==1){
				String s = "";
				if(currentWidget!=null){
					s += currentWidget.getIndex();
					RSWidget parent = currentWidget.getParentWidget();
					if(parent!=null){
						s = parent.getIndex()+":"+s;
						RSInterface parentIface = parent.getParentInterface();
						if(parentIface!=null)
							s = parentIface.index+":"+s;
					}
					else{
						RSInterface parentIface = currentWidget.getParentInterface();
						if(parentIface!=null)
							s = parentIface.index+":"+s;
					}
				}
				else
					s=currentString;
				return s+"";
			}
			return currentString;
		}
	}

	public void createClassNodes(){
		root.setAllowsChildren(true);
		for(RSInterface i : methods.widgets.getAll()){
			if(i!=null){
				DataNode n = new DataNode(""+i.index, i);
				boolean add=false;
				for(RSWidget ic : i.getChildren()){
					if(ic!=null){
						DataNode n2 = new DataNode(""+ic.getIndex(), ic);
						for(RSWidget ic2 : ic.getChildren()){
							if(ic2!=null)
								n2.addChild(new DataNode(""+ic2.getIndex(), ic2));
						}
						add=true;
						n.addChild(n2);
					}
				}
				if(add)
					root.addChild(n);
			}
		}
		treeModel.reload();
	}
	private String[] dataNames = new String[]{
			"widgetUID",
			"id",
			"type",
			"parentID",
			"boundsIndex",
			"visibleCycle",
			"displayCycle",
			"tooltip",
			"itemID",
			"itemQuantity",
			"itemIDs",
			"itemQuantities",
			"itemStackType",
			"targetVerb",
			"spellName",
			"dynamicX",
			"dynamicY",
			"dynamicWidth",
			"dynamicHeight",
			"originalX",
			"originalY",
			"originalWidth",
			"originalHeight",
			"relativeX",
			"relativeY",
			"paddingX",
			"paddingY",
			"menuType",
			"contentType",
			"width",
			"height",
			"oldWidth",
			"oldHeight",
			"scrollX",
			"scrollY",
			"scrollWidth",
			"scrollHeight",
			"disabledColor",
			"enabledColor",
			"disabledHoverColor",
			"enabledHoverColor",
			"filled",
			"alpha",
			"spriteID",
			"enabledSpriteID",
			"textureID",
			"borderThickness",
			"shadowColor",
			"disabledMediaType",
			"disabledMediaID",
			"enabledMediaType",
			"enabledMediaID",
			"disabledAnimationID",
			"enabledAnimationID",
			"modelOffsetX",
			"modelOffsetY",
			"childModelRotationHash",
			"rotationX",
			"rotationZ",
			"rotationY",
			"modelZoom",
			"fontID",
			"disabledText",
			"enabledText",
			"defaultMargin",
			"horizontalMargin",
			"verticalMargin",
			"textShadowed",
			"clickMask",
			"opbase",
			"flippedVertically",
			"flippedHorizontally",
			"currentFrameIndex",
			"currentFrameLength",
			"lineWidth",
			"spriteTiling",
			"pendingVarbitCount",
			"changedSkillsCount",
			"noClickThrough",
			"noScrollThrough",
			"isClickDown",
			"hovering",
			"hasListener",
			"hasScript",
			"isHidden",
			"renderAtPoint",
			"dragDeadZone",
			"dragDeadTime",
			"dragRenderBehavior",
			"actions",
			"configActions",
			"tableActions",
			"widgetVarps",
			"scriptOpcodes",
			"spriteIDs",
			"ySprites",
			"xSprites"
	};
}