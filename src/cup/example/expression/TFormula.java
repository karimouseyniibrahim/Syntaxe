package cup.example.expression;

import org.graphstream.ui.view.Viewer;

import cup.example.GraphStyle;

public class TFormula{
	public TFormula(AST e) {
		GraphStyle.style(e.graph);
		Viewer viewer = new Viewer(e.graph, Viewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
        viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.CLOSE_VIEWER);
        viewer.addDefaultView(true);
        viewer.enableAutoLayout();

        
	//	String a=e.name();
        
	//System.out.println(e);
	}

}
