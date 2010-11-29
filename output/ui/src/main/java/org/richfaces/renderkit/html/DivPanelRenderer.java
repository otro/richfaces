/*
 * JBoss, Home of Professional Open Source
 * Copyright ${year}, Red Hat, Inc. and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * buticon WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.richfaces.renderkit.html;

import org.richfaces.component.AbstractDivPanel;
import org.richfaces.renderkit.HtmlConstants;
import org.richfaces.renderkit.RenderKitUtils;
import org.richfaces.renderkit.RendererBase;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import java.io.IOException;
import java.util.Map;

import static org.richfaces.component.html.HtmlDivPanel.PropertyKeys.*;
import static org.richfaces.renderkit.RenderKitUtils.attributes;
import static org.richfaces.renderkit.RenderKitUtils.renderPassThroughAttributes;

/**
 * @author akolonitsky
 * 
 */
public class DivPanelRenderer extends RendererBase {

    private static final RenderKitUtils.Attributes PASS_THROUGH_ATTRIBUTES = attributes(
        lang,
        onclick,
        ondblclick,
        onmousedown,
        onmousemove,
        onmouseout,
        onmouseover,
        onmouseup,
        title,
        dir
    );

    protected static String attributeAsStyle(UIComponent comp, Enum attr) {
        String value = attributeAsString(comp, attr.toString());
        if (value.isEmpty()) {
            return "";
        }

        return styleElement(attr, value);
    }

    protected static String styleElement(Object name, Object value) {
        return new StringBuilder()
            .append(name).append(':').append(value).toString();
    }

    protected static String attributeAsString(UIComponent comp, Enum attr) {
        return attributeAsString(comp, attr.toString());
    }

    protected static String attributeAsString(UIComponent comp, String attr) {
        Object o = comp.getAttributes().get(attr);
        return o == null ? "" : o.toString();
    }

    /**
     * Capitalize the first character of the given string.
     *
     * @param string String to capitalize.
     * @return Capitalized string.
     * @throws IllegalArgumentException String is <kk>null</kk> or empty.
     */
    public static String capitalize(final String string) {
        return Character.toUpperCase(string.charAt(0)) + string.substring(1);
    }

    @Override
    protected void doEncodeBegin(ResponseWriter writer, FacesContext context, UIComponent component) throws IOException {
        super.doEncodeBegin(writer, context, component);

        writer.startElement(HtmlConstants.DIV_ELEM, component);
        writer.writeAttribute("id", component.getClientId(context), "clientId");
        writer.writeAttribute("class", getStyleClass(component), null);
        String style = getStyle(component);
        if (style != null && style.length() > 0) {
            writer.writeAttribute(HtmlConstants.STYLE_ATTRIBUTE, style, null);
        }
        
        renderPassThroughAttributes(context, component, getPassThroughAttributes());
    }

    protected String getStyle(UIComponent component) {
        return attributeAsString(component, "style");
    }

    protected RenderKitUtils.Attributes getPassThroughAttributes() {
        return PASS_THROUGH_ATTRIBUTES;
    }

    protected String getStyleClass(UIComponent component) {
        return attributeAsString(component, "styleClass");
    }

    @Override
    protected void doEncodeEnd(ResponseWriter writer, FacesContext context, UIComponent component) throws IOException {
        super.doEncodeEnd(writer, context, component);

        writeJavaScript(writer, context, component);

        writer.endElement(HtmlConstants.DIV_ELEM);
    }

    protected void writeJavaScript(ResponseWriter writer, FacesContext context, UIComponent component) throws IOException {
        Object script = getScriptObject(context, component);
        if (script != null) {
            writer.startElement(HtmlConstants.SCRIPT_ELEM, component);
            writer.writeAttribute(HtmlConstants.TYPE_ATTR, "text/javascript", "type");
            writer.writeText(script, null);
            writer.endElement(HtmlConstants.SCRIPT_ELEM);
        }
    }

    protected Object getScriptObject(FacesContext context, UIComponent component) {
        return null;
    }

    protected Map<String, Object> getScriptObjectOptions(FacesContext context, UIComponent component) {
        return null;
    }

    @Override
    protected Class<? extends UIComponent> getComponentClass() {
        return AbstractDivPanel.class;
    }
}

