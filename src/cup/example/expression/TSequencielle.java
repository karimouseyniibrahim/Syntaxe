package cup.example.expression;

public class TSequencielle extends AST {
	
	public TSequencielle(AST e1,AST e2) {
		
		this.T=TYPE.sequence;
		label.put(">>",">>P"+getIndex());
		children.addAll(e1.children);
		children.addAll(e2.children);
		formula=label.toString();
		ID =e1.formula+">>"+e2.formula;
		ELEMENTS.put(ID, this);
		graph.addNode(ID).setAttribute("ui.label",label.values() );
		labels.putAll(e1.labels);
		labels.putAll(e2.labels);
		infoschildren.putAll(e1.infoschildren);
		infoschildren.putAll(e2.infoschildren);
		for (AST ast : e1.children) {	
			if(ast.ID=="STOP"&&graph.getNode("STOP")==null) {
				new TAtom("STOP",TYPE.stop);
			}
			graph.addEdge(ID+ast.ID, ID, ast.ID, true).setAttribute("ui.label",e1.labelsEdgets.get(ast.ID) );
		}
		for (AST ast : e2.children) {
			if(ast.ID=="STOP"&&graph.getNode("STOP")==null) {
				new TAtom("STOP",TYPE.stop);
			}
			if(graph.getEdge(ID+ast.ID)==null)
				graph.addEdge(ID+ast.ID, ID, ast.ID, true).setAttribute("ui.label",e2.labelsEdgets.get(ast.ID) );
		}
		if(graph.getNode(e1.ID)!=null&&(!(e1.ID.equals(StaticElements.STOP)))) {			
			graph.removeNode(e1.ID);
			}
		if(graph.getNode(e2.ID)!=null&&(!e2.ID.equals(StaticElements.STOP))) {
			graph.removeNode(e2.ID);
			}
		
	}

}
