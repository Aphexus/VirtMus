/*
 * Copyright (C) 2006-2007  Gabriel Burca (gburca dash virtmus at ebixio dot com)
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package com.ebixio.thumbviewer;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import org.openide.util.ImageUtilities;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;

/**
 * Action which shows Thumbs component.
 */
public class ThumbsAction extends AbstractAction {
    
    public ThumbsAction() {
        super(NbBundle.getMessage(ThumbsAction.class, "CTL_ThumbsAction"));
        putValue(SMALL_ICON, new ImageIcon(ImageUtilities.loadImage(ThumbsTopComponent.ICON_PATH, true)));
    }
    
    @Override
    public void actionPerformed(ActionEvent evt) {
        TopComponent win = ThumbsTopComponent.findInstance();
        win.open();
        win.requestActive();
    }
    
}
