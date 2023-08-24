package dev.idw0309.counterstrike.data.weapon;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class WeaponData {
    private Material material;
    private String name;
    private String nbtData;

    public WeaponData(Material material, String name, String nbtData) {
        this.material = material;
        this.name = name;
        this.nbtData = nbtData;
    }

    public WeaponData(Material material, String name) {
        this.material = material;
        this.name = name;
    }

    /**
     * Factory method to create objects of this class
     * @param section a configuration section in WeaponData.yml
     * @return WeaponData instance or Null
     */
    public WeaponData createWeapon(ConfigurationSection section) {
        Material material = Material.getMaterial(section.getString("Material"));
        if (material == null) return null;
        String name = section.getString("Name");
        if (name == null) return null;
        if (section.getBoolean("NbtItem.isNbtItem", false)) {
            String nbtData1 = section.getString("NbtItem.NBT-Data");
            if (nbtData1 == null) return null;
            return new WeaponData(material, name, nbtData1);
        }
        return new WeaponData(material, name);
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNbtData() {
        return nbtData;
    }

    public void setNbtData(String nbtData) {
        this.nbtData = nbtData;
    }

    public ItemStack createItem() {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(name);
        itemStack.setItemMeta(meta);
        if (nbtData != null) {
            NBTItem nbtItem = new NBTItem(itemStack);
            nbtItem.setString("counterstrike_weapon_data", nbtData);
        }
        return itemStack;
    }

    public boolean compareItem(ItemStack itemStack) {
        NBTItem nbtItem = new NBTItem(itemStack);
        return "counterstrike_weapon_data".equals(nbtItem.getString("counterstrike_weapon_data"));
    }
}
