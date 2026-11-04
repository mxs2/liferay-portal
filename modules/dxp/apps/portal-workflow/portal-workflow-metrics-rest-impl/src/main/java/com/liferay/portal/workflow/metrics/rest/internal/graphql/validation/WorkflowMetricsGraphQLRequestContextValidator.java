/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.rest.internal.graphql.validation;

import com.liferay.portal.vulcan.graphql.validation.GraphQLRequestContext;
import com.liferay.portal.vulcan.graphql.validation.GraphQLRequestContextValidator;
import com.liferay.portal.workflow.metrics.rest.internal.security.permission.WorkflowMetricsPermissionUtil;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;

/**
 * @author Mateus Xavier
 */
@Component(service = GraphQLRequestContextValidator.class)
public class WorkflowMetricsGraphQLRequestContextValidator
	implements GraphQLRequestContextValidator {

	@Override
	public void validate(GraphQLRequestContext graphQLRequestContext)
		throws Exception {

		if (Objects.equals(
				graphQLRequestContext.getApplicationName(),
				"Liferay.Portal.Workflow.Metrics.REST")) {

			WorkflowMetricsPermissionUtil.check();
		}
	}

}