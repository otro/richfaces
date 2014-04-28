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
package org.richfaces.component.menu;

import static org.jboss.arquillian.warp.jsf.Phase.RENDER_RESPONSE;
import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.jboss.arquillian.warp.Inspection;
import org.jboss.arquillian.warp.jsf.AfterPhase;

/**
 * Warp inspection that verifies that menu was executed
 *
 * @author Lukas Fryc
 */
public class VerifyMenuAction extends Inspection {

    private static final long serialVersionUID = 1L;

    @Inject
    private DropDownMenuBean ddmBean;

    @AfterPhase(RENDER_RESPONSE)
    public void verifyAction() {
        assertEquals("action", ddmBean.getCurrent());
    }
}