package de.qaware.smartlab.core.data.generic;

public interface IDtoConverter<EntityT, DtoT> {
    DtoT toDto(EntityT entity);
    EntityT toEntity(DtoT dto);
}
