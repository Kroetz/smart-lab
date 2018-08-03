package de.qaware.smartlabcore.data.generic;

public interface IDtoConverter<EntityT, DtoT> {
    DtoT toDto(EntityT entity);
    EntityT toEntity(DtoT dto);
}
