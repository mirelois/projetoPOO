package com.poo.projeto.Community;

import com.poo.projeto.Community.Exceptions.NoHouseInPeriodException;
import com.poo.projeto.DailyCostAlgorithm.DailyCostAlgorithm;
import com.poo.projeto.Invoice.Invoice;
import com.poo.projeto.Provider.Exceptions.NoProvidersException;
import com.poo.projeto.Provider.Exceptions.ProviderAlreadyExistsException;
import com.poo.projeto.Provider.Exceptions.ProviderDoesntExistException;
import com.poo.projeto.Provider.Provider;
import com.poo.projeto.Provider.SerializableComparator;
import com.poo.projeto.SmartHouse.Division;
import com.poo.projeto.SmartHouse.Exceptions.*;
import com.poo.projeto.SmartHouse.SmartDevice;
import com.poo.projeto.SmartHouse.SmartHouse;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Community implements Serializable {
    private Map<String, Provider> providerMap; //name -> provider
    private Map<String, SmartHouse> smartHouseMap; //adress -> SmartHouse
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

    public List<SmartHouse> orderedHousesByConsumption(LocalDate start, LocalDate end) throws NoHouseInPeriodException {
        if (this.smartHouseMap.size() == 0) {
            //TODO não faz muito sentido este teste
            //TODO esta exception é mentirosa
            throw new NoHouseInPeriodException("Nenhuma casa com consumo no período de " + start.toString() + " a " + end.toString());
        }
        return this.smartHouseMap.values().stream().sorted((SerializableComparator<SmartHouse>) (o1, o2) -> Double.compare(o2.consumptionByPeriod(start, end), o1.consumptionByPeriod(start, end))).collect(Collectors.toList());
    }

    public List<Invoice> invoicesByProvider(String provider) throws ProviderDoesntExistException {
        Provider provider1 = this.getProviderByName(provider);
        List<Invoice> tmp = new ArrayList<>();
        provider1.getInvoiceMap().values().forEach(tmp::addAll);
        return tmp;
    }

    public Provider providerWithMostInvoicingVolume() throws NoProvidersException {
        Optional<Provider> max = this.providerMap.values().stream().max(Comparator.comparingDouble(Provider::invoicingVolume));
        if (max.isEmpty()) {
            throw new NoProvidersException("Nenhum fornecedor na simulação.");
        }
        return max.get().clone();
    }

    public SmartHouse houseWithMostConsumption(LocalDate start, LocalDate end) throws NoHouseInPeriodException {
        Optional<SmartHouse> max = this.smartHouseMap.values().stream().max(Comparator.comparingDouble(o -> o.consumptionByPeriod(start, end)));
        if (max.isEmpty()) {
            throw new NoHouseInPeriodException("No houses with faturation in period " + start.toString() + " to " + end.toString());
        }
        return max.get().clone();
    }

    private SmartHouse getSmartHouseByAddress(String address) throws AddressDoesntExistException {
        SmartHouse house = this.smartHouseMap.get(address);
        if (house == null)
            throw new AddressDoesntExistException("Failed to find: " + address);
        return house;
    }

    public boolean existsProvider(String provider) {
        return this.providerMap.containsKey(provider);
    }

    public boolean existsSmartHouse(String houseAddress) {
        return this.smartHouseMap.containsKey(houseAddress);
    }

    public int numberOfProviders() {
        return this.providerMap.size();
    }

    public int numberOfHouses() {
        return this.smartHouseMap.size();
    }

    public void addSmartHouse(SmartHouse house, String provider) throws AddressAlreadyExistsException, ProviderDoesntExistException {
        if(this.smartHouseMap.containsKey(house.getAddress())){
            throw new AddressAlreadyExistsException("The address " + house.getAddress() + " already exists!");
        }
        if(!(this.providerMap.containsKey(provider))){
            throw new ProviderDoesntExistException("The provider " + provider + " doesn't exist!");
        }
        house.setProvider(this.providerMap.get(provider));
        this.smartHouseMap.put(house.getAddress(), house);
    }

    public void addProvider(Provider provider) throws ProviderAlreadyExistsException {
        if (this.providerMap.containsKey(provider.getName()))
            throw new ProviderAlreadyExistsException("The provider " + provider.getName() + " already exists!");
        this.providerMap.put(provider.getName(), provider);
    }

    public void addSmartDevice(String address, String divisionName, SmartDevice smartDevice) throws AddressDoesntExistException {
        this.getSmartHouseByAddress(address).addSmartDevice(divisionName, smartDevice);
    }

    private Provider getProviderByName(String providerName) throws ProviderDoesntExistException {
        if (!this.providerMap.containsKey(providerName))
            throw new ProviderDoesntExistException("The provider " + providerName + " doesn't exist.");
        return this.providerMap.get(providerName);
    }

    public void addDivision(String address, Division division) throws DivisionAlreadyExistsException, AddressDoesntExistException {
        if (this.getSmartHouseByAddress(address).existsDivision(division.getName()))
            throw new DivisionAlreadyExistsException("The division " + division.getName() + " already exists!");
        this.smartHouseMap.get(address).addDivision(division);
    }

    @Override
    public String toString() {
        return "Providers:" + "\n   " + providerMap.values().stream().map(Provider::toString).collect(Collectors.joining("\n    ")) + "\n\n" +
                "SmartHouses:" + "\n    " + smartHouseMap.values().stream().map(SmartHouse::toString).collect(Collectors.joining("\n    ")) + "\n\n" +
                "Current Date:" + "\n   " + currentDate.toString();
    }

    public void setProviderDiscountFactor(String providerName, Double discountFactor) throws ProviderDoesntExistException {
        this.getProviderByName(providerName).setDiscountFactor(discountFactor);
    }

    public void setProviderAlgorithm(String providerName, DailyCostAlgorithm dailyCostAlgorithm) throws ProviderDoesntExistException {
        this.getProviderByName(providerName).setDailyCostAlgorithm(dailyCostAlgorithm);
    }

    public void setSmartHouseProvider(String address, String providerName) throws AddressDoesntExistException, ProviderDoesntExistException {
        this.getSmartHouseByAddress(address).setProvider(this.getProviderByName(providerName));
    }

    public void setBaseConsumption(String address, String device, Double baseConsumption) throws AddressDoesntExistException, DeviceDoesntExistException {
        this.getSmartHouseByAddress(address).setBaseConsumption(device, baseConsumption);
    }

    public void turnSmartDevice(String address, String smartDevice, boolean b) throws AddressDoesntExistException, DeviceDoesntExistException {
        SmartHouse house = this.getSmartHouseByAddress(address);
        if (b) {
            house.setDeviceOn(smartDevice);
        } else {
            house.setDeviceOff(smartDevice);
        }
    }

    public void turnDivision(String address, String division, boolean b) throws AddressDoesntExistException, DivisionDoesntExistException {
        SmartHouse house = this.getSmartHouseByAddress(address);
        if (b) {
            house.setDivisionOn(division);
        } else {
            house.setDivisionOff(division);
        }
    }

    public String houseToString(String houseName) throws AddressDoesntExistException {
        return this.getSmartHouseByAddress(houseName).toString();
    }

    public String providerToString(String providerName) throws ProviderDoesntExistException {
        return this.getProviderByName(providerName).toString();
    }

    public boolean existsDivision(String address, String division) throws AddressDoesntExistException {
        return this.getSmartHouseByAddress(address).existsDivision(division);
    }

    public boolean existsSmartDevice(String address, String smartDevice) throws AddressDoesntExistException {
        return this.getSmartHouseByAddress(address).existsDevice(smartDevice);
    }

    public boolean isSmartDeviceOn(String address, String smartDevice) throws AddressDoesntExistException, DeviceDoesntExistException {
        return this.getSmartHouseByAddress(address).isSmartDeviceOn(smartDevice);
    }

    public void advanceDate(LocalDate newDate) {
        for(SmartHouse house : this.getSmartHouseMap().values()) {
            house.invoiceEmission(this.getCurrentDate(), newDate);
        }
        this.setCurrentDate(newDate);
    }

    public String providersToString(){
        return "Todos os Fornecedores:\n    " + this.providerMap.values().stream().map(Provider::toString).collect(Collectors.joining("\n    "));
    }

    public String housesToString(){
        return "Todas as Casas:\n   " + this.smartHouseMap.values().stream().map(SmartHouse::toString).collect(Collectors.joining("\n    "));
    }

    public String housesByNifString(String nif){
        StringBuilder sb = new StringBuilder();
        for(SmartHouse smartHouse: this.smartHouseMap.values()){
            if(smartHouse.getNif().equals(nif)){
                sb.append(smartHouse.toString());
            }
        }
        return sb.toString();
    }
}
