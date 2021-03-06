/*
 * TagNode.java
 *
 * Copyright (C) 2006-2014  Gabriel Burca (gburca dash virtmus at ebixio dot com)
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

package com.ebixio.virtmus;

import org.openide.nodes.AbstractNode;

/**
 *
 * @author Gabriel Burca &lt;gburca dash virtmus at ebixio dot com&gt;
 */
public class TagNode extends AbstractNode {
    private final String tag;
    
    public TagNode(String tag, TagChildren children) {
        super(children);
        this.tag = tag;
        setIconBaseWithExtension("com/ebixio/virtmus/resources/TagNode.png");
    }
    
    @Override
    public boolean canRename() { return false; }
    
    @Override
    public String getName() {
        return tag;
    }
    
    @Override
    public String getDisplayName() {
        return getName();
    }
    
}
