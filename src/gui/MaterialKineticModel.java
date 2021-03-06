package starfish.core.gui;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import starfish.core.common.Starfish;
import starfish.core.materials.KineticMaterial;

/**
 *
 * @author Lubos Brieda
 */
@SuppressWarnings("serial")
public class MaterialKineticModel extends DefaultTableModel {

    /**
     *
     */
    public MaterialKineticModel() {
	}

    /**
     *
     * @param rowCount
     * @param columnCount
     */
    public MaterialKineticModel(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

    /**
     *
     * @param columnNames
     * @param rowCount
     */
    public MaterialKineticModel(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

    /**
     *
     * @param columnNames
     * @param rowCount
     */
    public MaterialKineticModel(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

    /**
     *
     * @param data
     * @param columnNames
     */
    public MaterialKineticModel(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

    /**
     *
     * @param data
     * @param columnNames
     */
    public MaterialKineticModel(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	@Override
    public boolean isCellEditable(int row, int column) {
       //all cells false
       return false;
    }
	
    /**
     *
     * @param rowData
     */
    public void addRowChangeStarfish(Object[] rowData) {
		/*KineticMaterial material = new KineticMaterial(rowData[0].toString(), 
		Double.valueOf(rowData[1].toString()), 
		Double.valueOf(rowData[2].toString()),
		Double.valueOf(rowData[3].toString()), false);
		//TODO: FIX: material.setInitValues(new String[] {rowData[4].toString()});
		Starfish.materials_module.getMaterialsList().add(material);
		*/
		super.addRow(rowData);
	}
	
    /**
     *
     * @param rowNumber
     */
    public void removeRowChangeStarfish(int rowNumber) {
		Starfish.materials_module.getMaterialsList().remove(Starfish.materials_module.getMaterial(super.getValueAt(rowNumber, 0).toString()));
		super.removeRow(rowNumber);
	}
	
    /**
     *
     */
    public void removeAllRows() {
		int num = super.getRowCount();
		for(int i = num-1; i >= 0; i--) {
			removeRow(i);
		}
	}
}
