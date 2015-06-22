package gui.metaDataProject;

import java.util.ArrayList;
import com.sciapp.table.styles.Style;

import gui.metaLibraries.logic.Role;
import gui.opdProject.OpdProject;
import gui.util.opcatGrid.GridPanel;

public class MetaGridPanel extends GridPanel {
	private MetaDataProject project;

	public MetaGridPanel(ArrayList columnsNames, MetaDataProject prj) {
		super(columnsNames);
		this.project = prj;
		this.getGrid().getStyleModel().addStyle(myStyle);
		this.getGrid().setDuplicateRows(false);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Style myStyle = new Style() {
		public void apply(java.awt.Component c, javax.swing.JTable table,
				int row, int column) {

			MetaColors colors = new MetaColors();
			int colorIdx = -1;
			if (project.isColoring()) {
				if ((column == project.getColoringIndex())) {
					MetaDataItem dataitem = (MetaDataItem) getGrid()
							.GetTag(row)[1];
					colorIdx = dataitem.getColoringLevel();

					if (column == 0) {
						c.setBackground(colors.getColor(colorIdx));
					} else {
						c.setForeground(colors.getColor(colorIdx));
					}
				}
			}
			if (project.getSourceType().type == MetaSourceType.OPCAT_PROJECT) {
				MetaDataAbstractItem item = (MetaDataAbstractItem) getGrid()
						.GetTag(row)[1];
				Role role = (Role) getGrid().GetTag(row)[0];
				
				
				
				OpdProject project = (OpdProject) role.getOntology().getProjectHolder() ; 
				
				//colors.setMax((int)project.getEntityMaxEntry() + 1 ) ; 				

				colorIdx = item.getColoringLevel(role.getOntology());

				if (colorIdx != -1) {
					if (column == 0) {
						c.setBackground(colors.getColor(colorIdx));
					}
				}

			}
		}
	};

}
