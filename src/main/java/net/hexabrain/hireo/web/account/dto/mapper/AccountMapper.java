package net.hexabrain.hireo.web.account.dto.mapper;

import org.mapstruct.Mapper;

import net.hexabrain.hireo.web.account.domain.Account;
import net.hexabrain.hireo.web.account.dto.AccountDto;
import net.hexabrain.hireo.web.bookmark.dto.mapper.BookmarkMapper;

@Mapper(componentModel = "spring", uses = {ProfileResponseMapper.class, BookmarkMapper.class})
public interface AccountMapper {
	AccountDto toDto(Account e);
}
