package org.richfaces.demo.tables;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.richfaces.JsfVersion;
import org.richfaces.demo.common.data.RandomHelper;
import org.richfaces.demo.tables.model.cars.InventoryItem;
import org.richfaces.demo.tables.model.cars.InventoryVendorItem;
import org.richfaces.demo.tables.model.cars.InventoryVendorList;

@ManagedBean(name = "carsBean")
@ViewScoped
public class CarsBean implements Serializable {
    private static final long serialVersionUID = -3832235132261771583L;
    private static final int DECIMALS = 1;
    private static final int CLIENT_ROWS_IN_AJAX_MODE = 15;
    private static final int ROUNDING_MODE = BigDecimal.ROUND_HALF_UP;
    private List<InventoryItem> allInventoryItems = null;
    private List<InventoryItem> shortInventoryList = null;
    private List<InventoryVendorList> inventoryVendorLists = null;
    private int currentCarIndex;
    private InventoryItem editedCar;
    private int page = 1;

    private int clientRows;

    public void switchAjaxLoading(ValueChangeEvent event) {
        this.clientRows = (Boolean) event.getNewValue() ? CLIENT_ROWS_IN_AJAX_MODE : 0;
    }

    public void remove() {
        allInventoryItems.remove(allInventoryItems.get(currentCarIndex));
    }

    public void store() {
        allInventoryItems.set(currentCarIndex, editedCar);
    }

    public List<SelectItem> getVendorOptions() {
        List<SelectItem> result = new ArrayList<SelectItem>();
        result.add(new SelectItem("", ""));
        for (InventoryVendorList vendorList : getInventoryVendorLists()) {
            result.add(new SelectItem(vendorList.getVendor()));
        }
        return result;
    }

    public List<String> getAllVendors() {
        List<String> result = new ArrayList<String>();
        for (InventoryVendorList vendorList : getInventoryVendorLists()) {
            result.add(vendorList.getVendor());
        }
        return result;
    }

    public List<InventoryVendorList> getInventoryVendorLists() {
        synchronized (this) {
            if (inventoryVendorLists == null) {
                inventoryVendorLists = new ArrayList<InventoryVendorList>();
                List<InventoryItem> inventoryItems = getShortInventoryList();

                Collections.sort(inventoryItems, new Comparator<InventoryItem>() {
                    public int compare(InventoryItem o1, InventoryItem o2) {
                        return o1.getVendor().compareTo(o2.getVendor());
                    }
                });
                Iterator<InventoryItem> iterator = inventoryItems.iterator();
                InventoryVendorList vendorList = new InventoryVendorList();
                vendorList.setVendor(inventoryItems.get(0).getVendor());
                while (iterator.hasNext()) {
                    InventoryItem item = iterator.next();
                    InventoryVendorItem newItem = new InventoryVendorItem();
                    itemToVendorItem(item, newItem);
                    if (!item.getVendor().equals(vendorList.getVendor())) {
                        inventoryVendorLists.add(vendorList);
                        vendorList = new InventoryVendorList();
                        vendorList.setVendor(item.getVendor());
                    }
                    vendorList.getVendorItems().add(newItem);
                }
                inventoryVendorLists.add(vendorList);
            }
        }
        return inventoryVendorLists;
    }

    private void itemToVendorItem(InventoryItem item, InventoryVendorItem newItem) {
        newItem.setActivity(item.getActivity());
        newItem.setChangePrice(item.getChangePrice());
        newItem.setChangeSearches(item.getChangeSearches());
        newItem.setDaysLive(item.getDaysLive());
        newItem.setExposure(item.getExposure());
        newItem.setInquiries(item.getInquiries());
        newItem.setMileage(item.getMileage());
        newItem.setMileageMarket(item.getMileageMarket());
        newItem.setModel(item.getModel());
        newItem.setPrice(item.getPrice());
        newItem.setPriceMarket(item.getPriceMarket());
        newItem.setPrinted(item.getPrinted());
        newItem.setStock(item.getStock());
        newItem.setVin(item.getVin());
    }

    public List<InventoryItem> getAllInventoryItems() {
        synchronized (this) {
            if (allInventoryItems == null) {
                allInventoryItems = new ArrayList<InventoryItem>();

                try {
                    allInventoryItems.addAll(createCar("Chevrolet", "Corvette", 5));
                    allInventoryItems.addAll(createCar("Chevrolet", "Malibu", 8));
                    allInventoryItems.addAll(createCar("Chevrolet", "Tahoe", 6));

                    allInventoryItems.addAll(createCar("Ford", "Taurus", 12));
                    allInventoryItems.addAll(createCar("Ford", "Explorer", 11));

                    allInventoryItems.addAll(createCar("Nissan", "Maxima", 9));
                    allInventoryItems.addAll(createCar("Nissan", "Frontier", 6));

                    allInventoryItems.addAll(createCar("Toyota", "4-Runner", 7));
                    allInventoryItems.addAll(createCar("Toyota", "Camry", 15));
                    allInventoryItems.addAll(createCar("Toyota", "Avalon", 13));

                    allInventoryItems.addAll(createCar("GMC", "Sierra", 8));
                    allInventoryItems.addAll(createCar("GMC", "Yukon", 10));

                    allInventoryItems.addAll(createCar("Infiniti", "G35", 6));
                    allInventoryItems.addAll(createCar("Infiniti", "EX35", 5));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return allInventoryItems;
    }
    
    private List<InventoryItem> getShortInventoryList() {
        synchronized (this) {
            if (shortInventoryList == null) {
                shortInventoryList = new ArrayList<InventoryItem>();

                try {
                    shortInventoryList.addAll(createCar("Chevrolet", "Corvette", 2));
                    shortInventoryList.addAll(createCar("Chevrolet", "Malibu", 4));
                    shortInventoryList.addAll(createCar("Chevrolet", "Tahoe", 1));

                    shortInventoryList.addAll(createCar("Ford", "Taurus", 5));
                    shortInventoryList.addAll(createCar("Ford", "Explorer", 3));

                    shortInventoryList.addAll(createCar("Nissan", "Maxima", 3));
                    shortInventoryList.addAll(createCar("Nissan", "Frontier", 4));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return shortInventoryList;
    }

    public List<InventoryItem> createCar(String vendor, String model, int count) {
        ArrayList<InventoryItem> iiList = null;

        try {
            int arrayCount = count;
            InventoryItem[] demoInventoryItemArrays = new InventoryItem[arrayCount];

            for (int j = 0; j < demoInventoryItemArrays.length; j++) {
                InventoryItem ii = new InventoryItem();

                ii.setVendor(vendor);
                ii.setModel(model);
                ii.setStock(RandomHelper.randomstring(6, 7));
                ii.setVin(RandomHelper.randomstring(17, 17));
                ii.setMileage(new BigDecimal(RandomHelper.rand(5000, 80000)).setScale(DECIMALS, ROUNDING_MODE));
                ii.setMileageMarket(new BigDecimal(RandomHelper.rand(25000, 45000)).setScale(DECIMALS, ROUNDING_MODE));
                ii.setPrice(new Integer(RandomHelper.rand(15000, 55000)));
                ii.setPriceMarket(new BigDecimal(RandomHelper.rand(15000, 55000)).setScale(DECIMALS, ROUNDING_MODE));
                ii.setDaysLive(RandomHelper.rand(1, 90));
                ii.setChangeSearches(new BigDecimal(RandomHelper.rand(0, 5)).setScale(DECIMALS, ROUNDING_MODE));
                ii.setChangePrice(new BigDecimal(RandomHelper.rand(0, 5)).setScale(DECIMALS, ROUNDING_MODE));
                ii.setExposure(new BigDecimal(RandomHelper.rand(0, 5)).setScale(DECIMALS, ROUNDING_MODE));
                ii.setActivity(new BigDecimal(RandomHelper.rand(0, 5)).setScale(DECIMALS, ROUNDING_MODE));
                ii.setPrinted(new BigDecimal(RandomHelper.rand(0, 5)).setScale(DECIMALS, ROUNDING_MODE));
                ii.setInquiries(new BigDecimal(RandomHelper.rand(0, 5)).setScale(DECIMALS, ROUNDING_MODE));
                demoInventoryItemArrays[j] = ii;
            }

            iiList = new ArrayList<InventoryItem>(Arrays.asList(demoInventoryItemArrays));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return iiList;
    }

    public int getCurrentCarIndex() {
        return currentCarIndex;
    }

    public void setCurrentCarIndex(int currentCarIndex) {
        this.currentCarIndex = currentCarIndex;
    }

    public InventoryItem getEditedCar() {
        return editedCar;
    }

    public void setEditedCar(InventoryItem editedCar) {
        this.editedCar = editedCar;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getClientRows() {
        return clientRows;
    }

    public void setClientRows(int clientRows) {
        this.clientRows = clientRows;
    }

    public void resetValues() {
        // reset input fields to prevent stuck values after a validation failure
        // not necessary in JSF 2.2+ (@resetValues on a4j:commandButton)
        if (!JsfVersion.getCurrent().isCompliantWith(JsfVersion.JSF_2_2)) {
            FacesContext fc = FacesContext.getCurrentInstance();
            UIComponent comp = fc.getViewRoot().findComponent("form:editGrid");

            ((EditableValueHolder) comp.findComponent("form:price")).resetValue();
            ((EditableValueHolder) comp.findComponent("form:mage")).resetValue();
            ((EditableValueHolder) comp.findComponent("form:vin")).resetValue();
        }
    }
}
