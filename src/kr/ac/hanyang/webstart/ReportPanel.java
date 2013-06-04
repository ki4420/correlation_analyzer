/*
 * $Id: ReportPanel.java,v 1.1 2011/02/08 01:54:52 space32 Exp $
 * Copyright (C) 2005 Rui Pedro Lopes (rlopes at ipb dot pt)
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Library General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Library General Public License for more details.
 *
 * You should have received a copy of the GNU Library General Public
 * License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 * Boston, MA  02111-1307, USA.
 *
 */

package kr.ac.hanyang.webstart;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;



/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI Builder, which is free for non-commercial use. If Jigloo is being used commercially (ie, by a corporation, company or business for any purpose whatever) then you should purchase a license for each developer using Jigloo. Please visit www.cloudgarden.com for details. Use of Jigloo implies acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class ReportPanel extends JPanel {
	
	/**
	 * @uml.property  name="textArea"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private JTextArea textArea;
	
	private static String type;
	
	private static String internalType;
	
	/**
	 * @return
	 * @uml.property  name="type"
	 */
	public static String getType() {
		return type;
	}
	
	/**
	 * @return
	 * @uml.property  name="internalType"
	 */
	public static String getInternalType() {
		return internalType;
	}
	
	/**
	 * @param type
	 * @uml.property  name="internalType"
	 */
	public static void setInternalType(String type) {
		internalType = type;
	}

	public void setReport(String r) {
		textArea.setText(r);
	}
	
	
	
    public ReportPanel(String r) {

        setLayout(new BorderLayout());
        this.setPreferredSize(new java.awt.Dimension(357, 232));

        JScrollPane jScrollPane1 = new JScrollPane();
    	add(jScrollPane1, BorderLayout.CENTER);
    	textArea = new JTextArea();
		jScrollPane1.setViewportView(textArea);
		setReport(r);
    }
}