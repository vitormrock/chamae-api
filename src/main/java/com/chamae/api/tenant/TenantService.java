package com.chamae.api.tenant;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class TenantService {

    private final TenantRepository tenantRepository;

    public Tenant createTenant(TenantCreateDTO dto) {

        // 1. Criamos a entidade vazia:
        if (tenantRepository.findBySlug(dto.slug()).isPresent()){
            throw new IllegalArgumentException("Já existe uma loja cadastrada com este link (slug).");
        }

        Tenant tenant = new Tenant();

        // 2. Copiamos os dados do DTO para a Entidade:
        tenant.setName(dto.name());
        tenant.setSlug(dto.slug());
        tenant.setAverageTicket(dto.averageTicket());

        // 3. Regras de negócio automáticas do ChamaÊ:
        tenant.setActive(true);
        tenant.setDueDate(LocalDate.now().plusDays(7));

        // 4. Salvamos no banco de dados:
        return tenantRepository.save(tenant);
    }
}
