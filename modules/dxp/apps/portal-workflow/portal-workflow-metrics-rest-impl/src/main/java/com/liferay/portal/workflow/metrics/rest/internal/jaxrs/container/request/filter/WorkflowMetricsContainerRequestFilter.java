/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.rest.internal.jaxrs.container.request.filter;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.workflow.metrics.rest.internal.security.permission.WorkflowMetricsPermissionUtil;

import jakarta.ws.rs.InternalServerErrorException;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Providers;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Mateus Xavier
 */
@Component(
	property = {
		"osgi.jaxrs.application.select=(osgi.jaxrs.name=Liferay.Portal.Workflow.Metrics.REST)",
		"osgi.jaxrs.extension=true",
		"osgi.jaxrs.name=Liferay.Portal.Workflow.Metrics.REST.WorkflowMetricsContainerRequestFilter"
	},
	scope = ServiceScope.PROTOTYPE, service = ContainerRequestFilter.class
)
public class WorkflowMetricsContainerRequestFilter
	implements ContainerRequestFilter {

	@Override
	public void filter(ContainerRequestContext containerRequestContext) {
		UriInfo uriInfo = containerRequestContext.getUriInfo();

		String path = uriInfo.getPath();

		if (path.endsWith("/openapi.json") || path.endsWith("/openapi.yaml")) {
			return;
		}

		try {
			WorkflowMetricsPermissionUtil.check();
		}
		catch (PrincipalException principalException) {
			ExceptionMapper<PrincipalException> exceptionMapper =
				_providers.getExceptionMapper(PrincipalException.class);

			containerRequestContext.abortWith(
				exceptionMapper.toResponse(principalException));
		}
		catch (PortalException portalException) {
			throw new InternalServerErrorException(portalException);
		}
	}

	@Context
	private Providers _providers;

}