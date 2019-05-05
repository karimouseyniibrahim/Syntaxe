package cup.example.expression;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.graphstream.graph.*;
import org.graphstream.graph.implementations.MultiGraph;

public class  AST   {
	public static Graph graph=new MultiGraph("Plan");
	public static TreeMap<String , AST> ELEMENTS=new TreeMap();
	public String ID,labeledge;

	public TreeMap<String , TreeMap<String,String>> labels=new TreeMap();
	public TreeMap<String , String> labelsEdgets=new TreeMap();
	public TreeMap<String , String> label=new  TreeMap();


	public static int index=0;
	public  int profondeur;
	public TreeMap<String , Infos> infoschildren=new TreeMap();
	public static int count =0;
	AST end;
	public int time=0,deltat=0,timemin=0,timemax=0;
	public String formula=null,localite,formattime;

	public int Nodeassociated=-1;
	public List<AST> children =new ArrayList();
	public TYPE T;
	
	public AST(String iD,  TYPE t) {
		super();
		ID =iD;
		label.put(iD,iD);
		formula=iD;
		graph.addNode(ID).setAttribute("ui.label",ID );
		ELEMENTS.put(ID, this);
		T = t;
	}
	public AST(String iD,  TYPE t,int time) {
		super();
		ID =iD;
		//label=iD;
		label.put("none","none");
		formula=iD;
		this.time=time;
		formattime="("+time+")";
		graph.addNode(ID).setAttribute("ui.label",label.values() );
		ELEMENTS.put(ID, this);
		T = t;
	}
	
	public AST(String iD,  TYPE t,int min,int max) {
		super();
		ID =iD;
		label.put("none","none");
		formula=iD;
		this.timemin=min;
		this.timemax=max;
		formattime="["+min+","+max+"]";
		graph.addNode(ID).setAttribute("ui.label",label.values() );
		ELEMENTS.put(ID, this);
		T = t;
	}

	public int getIndex() {
		return index++;
	}

	public AST(String end) {
		super();
		count++;
		ID ="END"+count;
		graph.addNode(ID).setAttribute("ui.label",ID );
		//ELEMENTS.put(ID, this);
	}

	public AST(String iD,TYPE t,String formul) {// parent paralism and others
		super();
		ID =iD;

		label.put("|||","|||P"+getIndex());
		formula=formul;
		ELEMENTS.put(ID, this);
		graph.addNode(ID).setAttribute("ui.label",label.values() );
		T = t;
	}

	public AST(TYPE node, String iD, AST parent, TreeMap labe,String labelEdge,String nodeReaplace) {//for children paralism
		ID =iD;
		T = node;
		AST elem= ELEMENTS.remove(nodeReaplace);
		 removeNode(nodeReaplace);
		//parent.labels.putAll(ID,labe.);

		this.label.putAll(labe);
		this.labeledge=labelEdge;

		parent.children.add(this);
		ELEMENTS.put(ID, this);

		graph.addNode(ID).setAttribute("ui.label",labe.values() );
		graph.addEdge(parent.ID+ID, parent.ID, ID, true).setAttribute("ui.label",labelEdge );

	}

	public static void removeNode(String id) {
		if(graph.getNode(id)!=null&&!StaticElements.STOP.equals(id))
			graph.removeNode(id);
			}
	public void addfils(AST a) {

		AST element=null;
		for (AST ast : children) {
			
			if((element==null)||(element!=null &&ast.profondeur>element.profondeur)) {
				element=ast;
			}
		}

		if(element!=null) {
			element.addfils(a);
			
		}else{
			removeEND();
			labels.put(a.ID,label);
			graph.addEdge(ID+a.ID, ID, a.ID, true).setAttribute("ui.label",label );
		}
	}

	public AST( String iD,AST right, TYPE t,int min,int max) {
		super();
		ID =iD+";"+right.formula;
		label.put("none","none");
		ELEMENTS.put(ID, this);
		graph.addNode(ID).setAttribute("ui.label",label.values() );
		T = t;
		this.timemax=max;
		this.timemin=min;
		formattime="("+timemin+","+timemax+")";
		graph.addEdge(ID+right.ID, ID, right.ID, true).setAttribute("ui.label",iD+formattime );
		children.add(right);
		//labels.put(right.ID,label);
		labelsEdgets.put(right.ID,iD+formattime);
	}
	public AST( String iD,AST right, TYPE t,int tim) {
		super();
		ID =iD+";"+right.formula;
		label.put("none","none");
		ELEMENTS.put(ID, this);
		graph.addNode(ID).setAttribute("ui.label",label.values());
		T = t;
		this.time=tim;
		formattime="("+tim+")";
		graph.addEdge(ID+right.ID, ID, right.ID, true).setAttribute("ui.label",iD+formattime );
		children.add(right);

		labels.put(right.ID,label);
		labelsEdgets.put(right.ID,iD+formattime);
	}
	public AST( String iD,AST right, TYPE t,int min,int max,String localite) {
		super();
		ID =iD+";"+right.formula;
		label.put("none","none");
		labeledge=iD;
		ELEMENTS.put(ID, this);
		/*if(right.T!=TYPE.stop)
			graph.getNode(right.ID).setAttribute("ui.label",localite );*/

		graph.addNode(ID).setAttribute("ui.label",label.values() );
		T = t;
		this.timemin=min;
		this.timemax=max;
		formattime="("+timemin+","+timemax+")";
		right.setLocaliteNode(localite);
		graph.addEdge(ID+right.ID, ID, right.ID, true).setAttribute("ui.label",labeledge+formattime );
		children.add(right);
		TreeMap<String,String> tr=new TreeMap();
		tr.put(localite,localite);
		labels.put(right.ID,tr);
		labelsEdgets.put(right.ID,labeledge+formattime);
	}

	public AST( String iD,AST right, TYPE t,String localite) {
		super();
		ID =iD+";"+right.formula;
		label.put("none","none");
		ELEMENTS.put(ID, this);
		graph.addNode(ID).setAttribute("ui.label",localite );
		T = t;
		graph.addEdge(ID+right.ID, ID, right.ID, true).setAttribute("ui.label",label );
		children.add(right);
		TreeMap<String,String> tr=new TreeMap();
		tr.put(localite,localite);
		labels.put(right.ID,tr);
		labelsEdgets.put(right.ID,label+formattime);
	}
	public AST( String iD,AST right, TYPE t,int tim,String localite) {
		super();
		ID =iD+";"+right.formula;
		label.put("none","none");
		labeledge=iD;
		ELEMENTS.put(ID, this);
/*		if(right.T!=TYPE.stop)
			graph.getNode(right.ID).setAttribute("ui.label",label );*/

		graph.addNode(ID).setAttribute("ui.label",label.values() );
		T = t;
		this.time=tim;
		formattime="("+tim+")";
		right.setLocaliteNode(localite);
		graph.addEdge(ID+right.ID, ID, right.ID, true).setAttribute("ui.label",labeledge+formattime );
		children.add(right);
		TreeMap<String,String> tr=new TreeMap();
		tr.put(localite,localite);
		labels.put(right.ID,tr);
		labelsEdgets.put(right.ID,labeledge+formattime);
	}
	public AST() {
		// TODO Auto-generated constructor stub
	}
	public Graph graph() {
		return graph;
	}

	public void setLocaliteNode(String localite){
		label.remove("none");
		if(T!=TYPE.stop) {
			label.put(localite,localite);
			graph.getNode(ID).setAttribute("ui.label",label.values() );
		}

		if(T!= TYPE.move){
		for (AST c:children){
			if(c.T!=TYPE.stop) {
				TreeMap<String,String> tr=new TreeMap();
				tr.put(localite,localite);
				labels.put(c.ID, tr);

				c.setLocaliteNode(localite);
			}
		}
		}

	}
	public List<AST> getChildren() {
		return children;
	}

	public void setChildren() {
		this.children = new ArrayList();
	}

	public void addEND() {
		
		
		end= graph.getNode("STOP")!=null?ELEMENTS.get("STOP"): new TAtom("STOP",TYPE.stop);
		end.Nodeassociated++;
		graph.addEdge(ID+end.ID, ID, end.ID, true).setAttribute("ui.label",T.equals(TYPE.exit)?"i"+formattime:label );
		TreeMap<String,String> tr=new TreeMap();
		tr.put(StaticElements.STOP,StaticElements.STOP);
		labels.put(end.ID,tr );

		labelsEdgets.put(end.ID,"i"+formattime );

		ELEMENTS.put(end.ID,end);
		children.add(end);
	}
	public void removeEND() {
//		graph.removeEdge(ID+end.ID);
//		graph.removeNode(end.ID);
		//graph.addNode(end).addAttribute("ui.label",end);
		//graph.addEdge(ID+end, ID, end, true).addAttribute("ui.label",label );
	}
	public String sublabelformula() {
		
		return formula.replaceFirst(label+";", "");
	}
	
	@Override
	public String toString() {

		String str=label+"[";
		for (AST ast : children) {
			str+=";"+ast;
		}
		return formula+label ;
	}
	
	
}
