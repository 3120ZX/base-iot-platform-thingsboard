/**
 * Copyright © 2016-2022 The Thingsboard Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.thingsboard.server.dao.sql.edge;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.thingsboard.server.dao.model.sql.EdgeEntity;
import org.thingsboard.server.dao.model.sql.EdgeInfoEntity;
import org.thingsboard.server.dao.model.sql.RuleChainEntity;

import java.util.List;
import java.util.UUID;

public interface EdgeRepository extends PagingAndSortingRepository<EdgeEntity, UUID> {

    @Query("SELECT d FROM EdgeEntity d WHERE d.tenantId = :tenantId " +
            "AND d.customerId = :customerId " +
            "AND LOWER(d.searchText) LIKE LOWER(CONCAT('%', :textSearch, '%'))")
    Page<EdgeEntity> findByTenantIdAndCustomerId(@Param("tenantId") UUID tenantId,
                                                 @Param("customerId") UUID customerId,
                                                 @Param("textSearch") String textSearch,
                                                 Pageable pageable);

    @Query("SELECT new org.thingsboard.server.dao.model.sql.EdgeInfoEntity(d, c.title, c.additionalInfo) " +
            "FROM EdgeEntity d " +
            "LEFT JOIN CustomerEntity c on c.id = d.customerId " +
            "WHERE d.id = :edgeId")
    EdgeInfoEntity findEdgeInfoById(@Param("edgeId") UUID edgeId);

    @Query("SELECT d FROM EdgeEntity d WHERE d.tenantId = :tenantId " +
            "AND LOWER(d.searchText) LIKE LOWER(CONCAT('%', :textSearch, '%'))")
    Page<EdgeEntity> findByTenantId(@Param("tenantId") UUID tenantId,
                                    @Param("textSearch") String textSearch,
                                    Pageable pageable);

    @Query("SELECT new org.thingsboard.server.dao.model.sql.EdgeInfoEntity(d, c.title, c.additionalInfo) " +
            "FROM EdgeEntity d " +
            "LEFT JOIN CustomerEntity c on c.id = d.customerId " +
            "WHERE d.tenantId = :tenantId " +
            "AND LOWER(d.searchText) LIKE LOWER(CONCAT('%', :textSearch, '%'))")
    Page<EdgeInfoEntity> findEdgeInfosByTenantId(@Param("tenantId") UUID tenantId,
                                                 @Param("textSearch") String textSearch,
                                                 Pageable pageable);

    @Query("SELECT d FROM EdgeEntity d WHERE d.tenantId = :tenantId " +
            "AND d.type = :type " +
            "AND LOWER(d.searchText) LIKE LOWER(CONCAT('%', :textSearch, '%'))")
    Page<EdgeEntity> findByTenantIdAndType(@Param("tenantId") UUID tenantId,
                                           @Param("type") String type,
                                           @Param("textSearch") String textSearch,
                                           Pageable pageable);

    @Query("SELECT new org.thingsboard.server.dao.model.sql.EdgeInfoEntity(d, c.title, c.additionalInfo) " +
            "FROM EdgeEntity d " +
            "LEFT JOIN CustomerEntity c on c.id = d.customerId " +
            "WHERE d.tenantId = :tenantId " +
            "AND d.type = :type " +
            "AND LOWER(d.searchText) LIKE LOWER(CONCAT('%', :textSearch, '%'))")
    Page<EdgeInfoEntity> findEdgeInfosByTenantIdAndType(@Param("tenantId") UUID tenantId,
                                                        @Param("type") String type,
                                                        @Param("textSearch") String textSearch,
                                                        Pageable pageable);

    @Query("SELECT d FROM EdgeEntity d WHERE d.tenantId = :tenantId " +
            "AND d.customerId = :customerId " +
            "AND d.type = :type " +
            "AND LOWER(d.searchText) LIKE LOWER(CONCAT('%', :textSearch, '%'))")
    Page<EdgeEntity> findByTenantIdAndCustomerIdAndType(@Param("tenantId") UUID tenantId,
                                                        @Param("customerId") UUID customerId,
                                                        @Param("type") String type,
                                                        @Param("textSearch") String textSearch,
                                                        Pageable pageable);

    @Query("SELECT new org.thingsboard.server.dao.model.sql.EdgeInfoEntity(a, c.title, c.additionalInfo) " +
            "FROM EdgeEntity a " +
            "LEFT JOIN CustomerEntity c on c.id = a.customerId " +
            "WHERE a.tenantId = :tenantId " +
            "AND a.customerId = :customerId " +
            "AND LOWER(a.searchText) LIKE LOWER(CONCAT('%', :searchText, '%'))")
    Page<EdgeInfoEntity> findEdgeInfosByTenantIdAndCustomerId(@Param("tenantId") UUID tenantId,
                                                                @Param("customerId") UUID customerId,
                                                                @Param("searchText") String searchText,
                                                                Pageable pageable);

    @Query("SELECT new org.thingsboard.server.dao.model.sql.EdgeInfoEntity(a, c.title, c.additionalInfo) " +
            "FROM EdgeEntity a " +
            "LEFT JOIN CustomerEntity c on c.id = a.customerId " +
            "WHERE a.tenantId = :tenantId " +
            "AND a.customerId = :customerId " +
            "AND a.type = :type " +
            "AND LOWER(a.searchText) LIKE LOWER(CONCAT('%', :textSearch, '%'))")
    Page<EdgeInfoEntity> findEdgeInfosByTenantIdAndCustomerIdAndType(@Param("tenantId") UUID tenantId,
                                                                       @Param("customerId") UUID customerId,
                                                                       @Param("type") String type,
                                                                       @Param("textSearch") String textSearch,
                                                                       Pageable pageable);

    @Query("SELECT ee FROM EdgeEntity ee, RelationEntity re WHERE ee.tenantId = :tenantId " +
            "AND ee.id = re.fromId AND re.fromType = 'EDGE' AND re.relationTypeGroup = 'EDGE' " +
            "AND re.relationType = 'Contains' AND re.toId = :entityId AND re.toType = :entityType " +
            "AND LOWER(ee.searchText) LIKE LOWER(CONCAT('%', :searchText, '%'))")
    Page<EdgeEntity> findByTenantIdAndEntityId(@Param("tenantId") UUID tenantId,
                                               @Param("entityId") UUID entityId,
                                               @Param("entityType") String entityType,
                                               @Param("searchText") String searchText,
                                               Pageable pageable);

    @Query("SELECT DISTINCT d.type FROM EdgeEntity d WHERE d.tenantId = :tenantId")
    List<String> findTenantEdgeTypes(@Param("tenantId") UUID tenantId);

    EdgeEntity findByTenantIdAndName(UUID tenantId, String name);

    List<EdgeEntity> findEdgesByTenantIdAndCustomerIdAndIdIn(UUID tenantId, UUID customerId, List<UUID> edgeIds);

    List<EdgeEntity> findEdgesByTenantIdAndIdIn(UUID tenantId, List<UUID> edgeIds);

    EdgeEntity findByRoutingKey(String routingKey);
}
