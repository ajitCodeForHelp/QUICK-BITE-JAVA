package com.quickBite.primary.mapper;

import com.quickBite.primary.pojo.menu.Category;
import com.quickBite.primary.pojo.menu.Item;
import com.quickBite.primary.pojo.menu.ItemAddOn;
import com.quickBite.primary.pojo.menu.Menu;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MenuMapper {
    MenuMapper MAPPER = Mappers.getMapper(MenuMapper.class);

    Menu.MenuItems mapToMenuItems(Item item);
    Menu.MenuCategory mapToMenuCategory(Category category);
    Menu.MenuItemAddOn mapToMenuItemAddOn(ItemAddOn itemAddOn);

}
