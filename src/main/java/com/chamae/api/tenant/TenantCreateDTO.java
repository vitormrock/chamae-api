package com.chamae.api.tenant;

import java.math.BigDecimal;

public record TenantCreateDTO(String name, String slug, BigDecimal averageTicket) {
}
