package com.poo.projeto;

import com.poo.projeto.SmartHouse.AddressAlreadyUsedException;
import com.poo.projeto.SmartHouse.Owner;
import com.poo.projeto.SmartHouse.SmartHouse;
import com.poo.projeto.provider.Provider;
import com.poo.projeto.provider.ProviderAlreadyExistsException;
import com.poo.projeto.provider.ProviderDoesntExistException;

import java.time.LocalDate;
import java.util.*;

public class Community {
    private Map<String, Provider> providerMap; //name -> provider
    private Map<String, SmartHouse> smartHouseMap; //adress -> SmartHouse??
    private LocalDate currentDate;

    public Community(Community c) {
        this.setProviderMap(c.providerMap);
        this.setSmartHouseMap(c.smartHouseMap);
        this.setCurrentDate(c.currentDate);
    }

    public Community() {
        this.setProviderMap(new HashMap<>());
        this.setSmartHouseMap(new HashMap<>());
        this.setCurrentDate(LocalDate.EPOCH);
    }

    public Community(Map<String, Provider> providerMap, Map<String, SmartHouse> smartHouseMap, LocalDate currentDate) {
        this.setProviderMap(providerMap);
        this.setSmartHouseMap(smartHouseMap);
        this.setCurrentDate(currentDate);
    }

    public Map<String, Provider> getProviderMap() {
        Map<String, Provider> hashMap = new HashMap<>();
        for (Map.Entry<String, Provider> entry : this.providerMap.entrySet()) {
            hashMap.put(entry.getKey(), entry.getValue().clone());
        }
        return hashMap;
    }

    public void setProviderMap(Map<String, Provider> providerMap) {
        this.providerMap = new HashMap<>();
        for (Map.Entry<String, Provider> entry : providerMap.entrySet()) {
            this.providerMap.put(entry.getKey(), entry.getValue().clone());
        }
    }

    public Map<String, SmartHouse> getSmartHouseMap() {
        Map<String, SmartHouse> smartHouseMap = new HashMap<>();
        for (Map.Entry<String, SmartHouse> entry : this.smartHouseMap.entrySet()) {
            smartHouseMap.put(entry.getKey(), entry.getValue().clone());
        }
        return smartHouseMap;
    }

    public void setSmartHouseMap(Map<String, SmartHouse> smartHouseMap) {
        this.smartHouseMap = new HashMap<>();
        for (Map.Entry<String, SmartHouse> entry : smartHouseMap.entrySet()) {
            this.smartHouseMap.put(entry.getKey(), entry.getValue().clone());
        }
    }

    public LocalDate getCurrentDate() {
        return LocalDate.of(this.currentDate.getYear(), this.currentDate.getMonth(), this.currentDate.getDayOfMonth());
    }

    public void setCurrentDate(LocalDate currentDate) {
        this.currentDate = LocalDate.of(currentDate.getYear(), currentDate.getMonth(), currentDate.getDayOfMonth());
    }

    public SortedSet<SmartHouse> orderedHousesWithMostConsumption(LocalDate start, LocalDate end) {
        SortedSet<SmartHouse> sortedSet = new TreeSet<>(Comparator.comparingDouble(o -> o.consumptionByPeriod(start, end)));
        sortedSet.addAll(this.smartHouseMap.values());
        return sortedSet;
    }

    public List<Invoice> invoicesFromProvider(String provider) {
        Provider provider1 = this.providerMap.get(provider);
        List<Invoice> tmp = new ArrayList<>();
        if (provider1 == null) {
            //TODO pode dar throw NoProvider exception
        } else {
            provider1.getInvoiceMap().values().forEach(tmp::addAll);
        }
        return tmp;
    }

    public Provider providerWithMostInvoicingVolume() {
        Optional<Provider> max = this.providerMap.values().stream().max(Comparator.comparingDouble(Provider::invoicingVolume));
        if (max.isEmpty()) {
            //TODO return NoProviders exception
            return null;
        } else {
            return max.get().clone();
        }
    }

    public SmartHouse houseWithMostConsumption(LocalDate start, LocalDate end) {
        Optional<SmartHouse> max = this.smartHouseMap.values().stream().max(Comparator.comparingDouble(o -> o.consumptionByPeriod(start, end)));
        if (max.isEmpty()) {
            //TODO return NoHouses exception
            return null;
        } else {
            return max.get().clone();
        }
    }

    public void advanceDate(LocalDate newDate) {
        for(SmartHouse house : this.smartHouseMap.values()) {
            house.invoiceEmission(this.currentDate, newDate);
        }
        this.currentDate = newDate;
    }

    public void addSmartHouse(SmartHouse house) {
        //TODO throw HouseNull
        if (house != null)
            this.smartHouseMap.put(house.getAddress(), house.clone());
    }

    public void addProvider(Provider provider) {
        //TODO throw ProviderNull
        if (provider != null)
            this.providerMap.put(provider.getName(), provider.clone());
    }

    public void addSmartHouse(String address, String name, String nif, String provider) throws AddressAlreadyUsedException, ProviderDoesntExistException {
        if(this.smartHouseMap.containsKey(address)){
            throw new AddressAlreadyUsedException("The address " + address + " already exists!");
        }
        if(!(this.providerMap.containsKey(provider))){
            throw new ProviderDoesntExistException("The provider " + provider + " doesn't exist!");
        }
        SmartHouse newSmartHouse = new SmartHouse(address, name, nif, this.providerMap.get(provider));
        this.smartHouseMap.put(address, newSmartHouse);
    }


    public boolean existsProvider(String provider) {
        return this.providerMap.containsKey(provider);
    }

    public boolean existsSmartHouse(String houseAddress) {
        return this.smartHouseMap.containsKey(houseAddress);
    }
    
    public void addProvider(String name) throws ProviderAlreadyExistsException {
        if (this.providerMap.containsKey(name))
            throw new ProviderAlreadyExistsException("The provider " + name + " already exists!");
        Provider provider = new Provider(name);
        this.providerMap.put(name, provider);
    }
}
