/*
 * Copyright (C) 2003-2007 eXo Platform SAS.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see<http://www.gnu.org/licenses/>.
 */
package org.exoplatform.ecm.webui.component.explorer.symlink;

import javax.jcr.Item;
import javax.jcr.Node;

import org.exoplatform.ecm.webui.component.explorer.UIJCRExplorer;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;
import org.exoplatform.services.wcm.core.NodeLocation;
import org.exoplatform.webui.config.annotation.ComponentConfig;
import org.exoplatform.webui.config.annotation.EventConfig;
import org.exoplatform.webui.core.UIContainer;
import org.exoplatform.webui.event.Event;
import org.exoplatform.webui.event.EventListener;

/**
 * Created by The eXo Platform SARL
 * Author : Dang Van Minh
 *          minh.dang@exoplatform.com
 * May 25, 2007 8:59:34 AM
 */
@ComponentConfig(
    template = "app:/groovy/webui/component/explorer/UITabPaneWithAction.gtmpl",
    events = {
        @EventConfig(listeners = UISymLinkContainer.CloseActionListener.class)
    }
)
public class UISymLinkContainer extends UIContainer {

  private static final Log LOG = ExoLogger.getLogger(UISymLinkContainer.class);
  
  private NodeLocation uploadedNode_ ;

  public UISymLinkContainer() throws Exception {
  }

  public String[] getActions() {return new String[] {"AddMetadata","Close"} ;}

  public Node getEditNode(String nodeType) throws Exception {
    Node uploadedNode = getUploadedNode();
    try {
      Item primaryItem = uploadedNode.getPrimaryItem() ;
      if(primaryItem == null || !primaryItem.isNode()) return uploadedNode;
      if(primaryItem != null && primaryItem.isNode()) {
        Node primaryNode = (Node) primaryItem ;
        if(primaryNode.isNodeType(nodeType)) return primaryNode ;
      }
    } catch(Exception e) {
      if (LOG.isWarnEnabled()) {
        LOG.warn(e.getMessage());
      }
    }
    return uploadedNode;
  }

  public void setUploadedNode(Node node) throws Exception { 
    uploadedNode_ = NodeLocation.getNodeLocationByNode(node); 
  }
  public Node getUploadedNode() { 
    return NodeLocation.getNodeByLocation(uploadedNode_); 
  }

  static public class CloseActionListener extends EventListener<UISymLinkContainer> {
    public void execute(Event<UISymLinkContainer> event) throws Exception {
      UIJCRExplorer uiExplorer = event.getSource().getAncestorOfType(UIJCRExplorer.class);
      uiExplorer.cancelAction() ;
    }
  }
}
