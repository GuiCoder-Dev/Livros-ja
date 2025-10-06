package com.livrosja.security.annotationPermition

import org.springframework.security.access.prepost.PreAuthorize

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@PreAuthorize("hasRole('ROLE_ADMIN') || @bookPermissionEvaluator.hasAccess(authentication, #id)")
annotation class BookResourceAccess
