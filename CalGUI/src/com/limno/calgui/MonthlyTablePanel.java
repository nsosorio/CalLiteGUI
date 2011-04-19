package com.limno.calgui;
import hec.heclib.util.HecTime;
import hec.io.TimeSeriesContainer;
import java.awt.Dimension;
import java.text.DecimalFormat;
import java.util.Vector;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
public class MonthlyTablePanel extends JPanel {
	MonthlyTablePanel(TimeSeriesContainer tsc) {
		super();
		DecimalFormat df1 = new DecimalFormat("#.#");
		DecimalFormat df2 = new DecimalFormat("#.##");
		Vector<String> data = new Vector<String>();
		HecTime ht = new HecTime();
		// Count forward to right month - hardcoded to 10 for now
		// TODO - match to input
		int first = 0;
		ht.set(tsc.times[first]);
		while (ht.month() != 10) {
			first++;
			ht.set(tsc.times[first]);
		}
		double sum = 0;
		for (int i = first; i < tsc.numberValues; i++) {
			ht.set(tsc.times[i]);
			int y = ht.year();
			int m = ht.month();
			int wy = (m < 10) ? y : y + 1;
			if ((i - first) % 12 == 0) {
				if (i != first)
					data.addElement(df1.format(sum));
				sum = 0;
				data.addElement(Integer.toString(wy));
			}
			sum = sum + tsc.values[i];
			data.addElement(df1.format(tsc.values[i]));
		}
		data.addElement(df1.format(sum));
		Vector<String> columns = new Vector<String>(14);
		columns.addElement("WY");
		columns.addElement("Oct");
		columns.addElement("Nov");
		columns.addElement("Dec");
		columns.addElement("Jan");
		columns.addElement("Feb");
		columns.addElement("Mar");
		columns.addElement("Apr");
		columns.addElement("May");
		columns.addElement("Jun");
		columns.addElement("Jul");
		columns.addElement("Aug");
		columns.addElement("Sep");
		columns.addElement("Ann");
		SimpleTableModel2 model = new SimpleTableModel2(data, columns);
		JTable table = new JTable(model);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		TableColumn col = table.getColumnModel().getColumn(0);
		col.setPreferredWidth(50);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(750, 600));
		add(scrollPane);
	}
}
class SimpleTableModel2 extends AbstractTableModel {
	protected Vector<String> data;
	protected Vector<String> columnNames;
	public SimpleTableModel2(Vector<String> datain, Vector<String> columnin) {
		data = datain;
		columnNames = columnin;
	}
	public int getRowCount() {
		return data.size() / getColumnCount();
	}
	public int getColumnCount() {
		return columnNames.size();
	}
	public String getColumnName(int columnIndex) {
		String colName = "";
		if (columnIndex <= getColumnCount())
			colName = (String) columnNames.elementAt(columnIndex);
		return colName;
	}
	public Class getColumnClass(int columnIndex) {
		return String.class;
	}
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
	public Object getValueAt(int rowIndex, int columnIndex) {
		return (String) data.elementAt((rowIndex * getColumnCount()) + columnIndex);
	}
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		data.setElementAt((String) aValue, ((rowIndex * getColumnCount()) + columnIndex));
		// return;
	}
}
