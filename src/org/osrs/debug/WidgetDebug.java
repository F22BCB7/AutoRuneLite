package org.osrs.debug;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JPanel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

import org.osrs.api.methods.MethodContext;
import org.osrs.api.objects.RSInterface;
import org.osrs.api.objects.RSWidget;
import org.osrs.api.wrappers.Client;
import org.osrs.api.wrappers.Widget;
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
	}
	public Graphics paint(Graphics g){
		try {
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		paintPanel.getGraphics().drawImage(paintPanelImage, 0, 0, null);
		return g;
	}
	public class WidgetDebugger extends javax.swing.JFrame implements MouseWheelListener{
		private static final long serialVersionUID = 1L;
		public WidgetDebugger(){
			setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
			addWindowListener(new WindowAdapter() {
			    public void windowClosing(WindowEvent e) {
			        Data.clientFrame.widgetDebuggerOption.setState(false);
			        currentDataIndex=0;
			        widgetToDisplay=null;
			    }
			});
			paintPanel = new JPanel();
			jLabel1 = new javax.swing.JLabel();
			jLabel1.setFont(new java.awt.Font("Trebuchet MS", 1, 14));
			jLabel1.setText("Widget Debugger");

			jButton3 = new javax.swing.JButton();
			jButton3.setText("Refresh");
			jButton3.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					jButton3ActionPerformed(evt);
				}
			});

			jScrollPane1 = new javax.swing.JScrollPane();
			root = new DataNode("Runescape Widgets");
			treeModel = new DefaultTreeModel(root);
			createClassNodes();
			jTree1 = new javax.swing.JTree(root);
			jTree1.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
			jTree1.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
				public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
					jTree1ValueChanged(evt);
				}
			});
			jScrollPane1.setViewportView(jTree1);
			javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
			getContentPane().setLayout(layout);
			layout.setHorizontalGroup(
					layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
					.addGroup(layout.createSequentialGroup()
							.addContainerGap()
							.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
									.addGroup(layout.createSequentialGroup()
											.addComponent(jLabel1)
											.addGap(18, 18, 18)
											.addComponent(jButton3)
											.addGap(18)
											.addComponent(paintPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
											.addGap(18))
									.addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
											.addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 210, 210)
											.addContainerGap(18, 18))))
					);
			layout.setVerticalGroup(
					layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
					.addGroup(layout.createSequentialGroup()
							.addContainerGap()
							.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
									.addComponent(jLabel1)
									.addComponent(jButton3))
							.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
							.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
									.addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
									.addGap(18)
									.addComponent(paintPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
									.addGap(18))
							.addContainerGap())
					);
			paintPanel.addMouseWheelListener(this);
			pack();
		}
		@Override
		public void setSize(int width, int height){
			int widthDiff = -(this.getWidth()-width);
			paintPanelImage = new BufferedImage(paintPanel.getWidth()+widthDiff, height, BufferedImage.TYPE_INT_RGB);
			paintPanel.setSize(paintPanel.getWidth()+widthDiff, height);
			super.setSize(width, height);
		}
		private javax.swing.JButton jButton3;
		private javax.swing.JLabel jLabel1;
		private javax.swing.JScrollPane jScrollPane1;
		private javax.swing.JTree jTree1;
		private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
			currentDataIndex=0;
			jTree1.clearSelection();
			root = new DataNode("Runescape Widgets");
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
			if(root.getChildCount()<1)
				root.addChild(new DataNode("None Found"));
			treeModel = new DefaultTreeModel(root);
			jTree1.setModel(treeModel);
			widgetToDisplay=null;
		}                                        

		private void jTree1ValueChanged(javax.swing.event.TreeSelectionEvent evt) {
			if(jTree1.getSelectionPath()!=null){
				DataNode selected = (DataNode) jTree1.getSelectionPath().getLastPathComponent();
				widgetToDisplay = selected.currentInterfaceChild;
			}
		}
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
	}
	private DataNode root;
	private DefaultTreeModel treeModel;
	public class DataNode extends DefaultMutableTreeNode {
		private static final long serialVersionUID = 1L;
		private boolean areChildrenDefined = false;
		private int numChildren;
		public ArrayList<DataNode> childrenArray = new ArrayList<DataNode>();
		public String currentString;//Current name of class/field/method

		public RSInterface currentInterface;
		public RSWidget currentInterfaceChild;

		public DataNode(String s) {
			super(s);
			numChildren = 0;
			currentString=s;
		}
		public DataNode(String s, RSInterface i){
			super(s);
			numChildren=0;
			currentString=s;
			currentInterface=i;
		}
		public DataNode(String s, RSWidget i){
			super(s);
			numChildren=0;
			currentString=s;
			currentInterfaceChild=i;
		}
		@Override
		public boolean isLeaf() {
			return (currentInterfaceChild!=null && currentInterfaceChild.getChildren().length==0);
		}
		public int getNodeCount(){
			if(childrenArray.size()>0){
				int count=1;
				for(DataNode n : childrenArray)
					count+=n.getNodeCount();
				return count;
			}
			return 1;
		}
		@Override
		public int getChildCount() {
			if (!areChildrenDefined)
				return 0;
			return numChildren;
		}
		public DataNode getChildByText(String s){
			for(DataNode n : childrenArray)
				if(n.toString().equals(s))
					return n;
			return null;
		}
		public void removeChildren(){
			childrenArray=new ArrayList<DataNode>();
			numChildren=0;
			removeAllChildren();
			areChildrenDefined=false;
		}
		public void addChild(DataNode d){
			areChildrenDefined=true;
			add(d);
			childrenArray.add(d);
			numChildren++;	
		}
		public String toString() {
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
		treeModel.reload(root);
	}
	private String[] dataNames = new String[]{
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