/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.richfaces.ui.attribute;

import org.richfaces.cdk.annotations.Attribute;
import org.richfaces.cdk.annotations.Description;

/**
 * Defines attributes for JSF AJAX parameters.
 *
 * @author Lukas Fryc
 */
public interface AjaxProps extends AjaxEventsProps {

    /**
     * IDs of components that will participate in the "execute" portion of the Request Processing Lifecycle. Can be a single ID,
     * a space or comma separated list of IDs, or an EL Expression evaluating to an array or Collection. Any of the keywords
     * "@this", "@form", "@all", "@none", "@region" may be specified in the identifier list. Some components make use of
     * additional keywords
     */
    @Attribute(description = @Description("IDs of components that will participate in the \"execute\" portion of the Request Processing Lifecycle. Can be a single ID, a space or comma separated list of IDs, or an EL Expression evaluating to an array or Collection. Any of the keywords \"@this\", \"@form\", \"@all\", \"@none\", \"@region\" may be specified in the identifier list. Some components make use of additional keywords"))
    Object getExecute();

    /**
     * IDs of components that will participate in the "render" portion of the Request Processing Lifecycle. Can be a single ID,
     * a space or comma separated list of Id's, or an EL Expression evaluating to an array or Collection. Any of the keywords
     * "@this", "@form", "@all", "@none", "@region" may be specified in the identifier list. Some components make use of
     * additional keywords
     */
    @Attribute(description = @Description("IDs of components that will participate in the \"render\" portion of the Request Processing Lifecycle. Can be a single ID, a space or comma separated list of Id's, or an EL Expression evaluating to an array or Collection. Any of the keywords \"@this\", \"@form\", \"@all\", \"@none\", \"@region\" may be specified in the identifier list. Some components make use of additional keywords"))
    Object getRender();

    /**
     * If "true", render only those ids specified in the "render" attribute, forgoing the render of the auto-rendered panels
     */
    @Attribute(defaultValue = "false", description = @Description("If \"true\", render only those ids specified in the \"render\" attribute, forgoing the render of the auto-rendered panels"))
    boolean isLimitRender();

    /**
     * Name of the request status component that will indicate the status of the Ajax request
     */
    @Attribute(description = @Description("Name of the request status component that will indicate the status of the Ajax request"))
    String getStatus();

    /**
     * Serialized (on default with JSON) data passed to the client by a developer on an AJAX request. It's accessible via
     * "event.data" syntax. Both primitive types and complex types such as arrays and collections can be serialized and used
     * with data.
     */
    @Attribute(description = @Description("Serialized (on default with JSON) data passed to the client by a developer on an AJAX request. It's accessible via \"event.data\" syntax. Both primitive types and complex types such as arrays and collections can be serialized and used with data"))
    Object getData();
}
