package com.mamrez.sample.service.mapper;

import com.mamrez.sample.domain.*;
import com.mamrez.sample.service.dto.GroupDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Group} and its DTO {@link GroupDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface GroupMapper extends EntityMapper<GroupDTO, Group> {

    @Mapping(source = "user.id", target = "userId")
    GroupDTO toDto(Group group);

    @Mapping(source = "userId", target = "user")
    Group toEntity(GroupDTO groupDTO);

    default Group fromId(Long id) {
        if (id == null) {
            return null;
        }
        Group group = new Group();
        group.setId(id);
        return group;
    }
}
