package com.mamrez.sample.service.mapper;

import com.mamrez.sample.domain.*;
import com.mamrez.sample.service.dto.PageDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Page} and its DTO {@link PageDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface PageMapper extends EntityMapper<PageDTO, Page> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    PageDTO toDto(Page page);

    @Mapping(source = "userId", target = "user")
    Page toEntity(PageDTO pageDTO);

    default Page fromId(Long id) {
        if (id == null) {
            return null;
        }
        Page page = new Page();
        page.setId(id);
        return page;
    }
}
