package cup.example.expression;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class TParallele extends AST{

	public static AST TParallele(AST e1, AST e2) {
		// TODO Auto-generated constructor stub
		// Parent of parallel
		AST parent= new AST(e1+"|||"+e2,TYPE.node,e1.label+"|||"+e2.label);
		//parent.labels.putAll(e1.label);
		//parent.labels.putAll(e2.label);
		makeparalle(parent,e1, e2);

		if(!graph.getNode("STOP").getEachEnteringEdge().iterator().hasNext()){
			graph.removeNode("STOP");
		}
		return parent;
	}


	public static void makeparalle(AST parent,AST e1, AST e2) {
		for (AST ast:e1.children ) {
			if(graph.getNode(ast+"|||"+e2)==null){//first modification
				TreeMap<String,String> tree=new TreeMap();
				tree.putAll( e1.labels.get(ast.ID));
				if(e2!=null)
					tree.putAll(e2.label);
				else
					tree.put(StaticElements.STOP,StaticElements.STOP);

				AST f1= new AST(ast.T,ast+"|||"+e2,parent,tree,e1.labelsEdgets.get(ast.ID),ast.ID);//direct child creation
				makeparalle(f1,ast,e2);
				if(e2.formula==null){
					makeparalle(f1,ast,e2);
				}
				}
			else{
				AST elementEXIST=ELEMENTS.get(ast+"|||"+e2);
				if(!parent.children.contains(elementEXIST)){
					parent.children.add(elementEXIST);
					parent.labels.put(elementEXIST.ID,e1.labels.get(ast.ID));
					parent.labelsEdgets.put(elementEXIST.ID,e1.labelsEdgets.get(ast.ID));
					graph.addEdge(parent.ID+elementEXIST, parent.ID, elementEXIST.ID, true).setAttribute("ui.label",e1.labelsEdgets.get(ast.ID) );
				}
			}
		}

		// third modification for the way possibilty
		for (AST ast:e2.children ) {
			if(graph.getNode(e1+"|||"+ast)==null){
				AST f1;
				if(e1.formula==null&&ast.formula==null) {
					f1= ast;
				} else {
					TreeMap<String,String> tree=new TreeMap();
					if (ast!=null)
						tree.putAll( e2.labels.get(ast.ID));
					else
						tree.put(StaticElements.STOP,StaticElements.STOP);
					if(e2!=null)
						tree.putAll(e1.label);
					else
						tree.put(StaticElements.STOP,StaticElements.STOP);

					f1= new AST(ast.T,e1+"|||"+ast,parent,tree,e2.labelsEdgets.get(ast.ID),ast.ID);
				}
				makeparalle(f1,e1,ast);
				if(e1.formula==null){
					makeparalle(f1,e1,ast);
				}
			}else{
				AST elementEXIST=ELEMENTS.get(e1+"|||"+ast);
				if(!parent.children.contains(elementEXIST)){
					parent.children.add(elementEXIST);
					parent.labels.put(elementEXIST.ID,e2.labels.get(ast.ID));
					if(graph.getEdge(parent.ID+elementEXIST)==null)
						graph.addEdge(parent.ID+elementEXIST, parent.ID, elementEXIST.ID, true).setAttribute("ui.label",e2.labelsEdgets.get(ast.ID) );
				}
				//System.out.println(e1+"|||"+ast);
			}
		}
		 removeNode(e1.ID);
		 removeNode(e2.ID);
	}
}
