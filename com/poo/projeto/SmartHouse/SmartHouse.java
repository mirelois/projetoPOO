package com.poo.projeto.SmartHouse;

import com.poo.projeto.Invoice;
import com.poo.projeto.provider.Provider;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class SmartHouse {

    private String address;

    private Provider provider;

    private List<Invoice> invoices;

    private Owner owner;
    private Map<String, String> devices; // id -> string da divisão
    private Map<String, Division> divisions; // string da divisão -> Divisão (classe)

    /**
     * Constructor for objects of class CasaInteligente
     */
    public SmartHouse() {
        this.address = "";
        this.devices = new HashMap<>();
        this.divisions = new HashMap<>();
        this.provider = new Provider();
        this.owner = new Owner();
        this.invoices = new ArrayList<>();
    }

    public SmartHouse(Owner owner, String address, Map<String, String> devices, Map<String, Division> divisions, Provider provider, List<Invoice> invoices) {
        //TODO não está a criar novas estruturas então de fora podem destruí-las
        this.address = address;
        this.devices = devices.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        this.divisions = divisions.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, div -> div.getValue().clone()));
        this.provider = provider;
        this.owner = owner;
        this.invoices = invoices;
    }

    public SmartHouse(SmartHouse smartHouse){
        this.address = smartHouse.getAddress();
        this.devices = smartHouse.getDevices();
        this.divisions = smartHouse.getDivisions();
        this.provider = smartHouse.getProvider();
        this.owner = smartHouse.getOwner();
        this.invoices = smartHouse.getInvoices();
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Map<String, String> getDevices() {
        return this.devices.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public void setDevices(Map<String, String> devices) {
        //TODO não está a criar uma nova estrutura então de fora podem destruí-la
        this.devices = devices;
    }

    public Map<String, Division> getDivisions(){
        return this.divisions.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, value -> value.getValue().clone()));
    }

    public void setDivisions(Map<String, Division> divisions){
        //TODO não está a clonar as divisões
        //TODO não está a criar uma nova estrutura então de fora podem destruí-la
        this.divisions = divisions;
    }

    public Provider getProvider(){
        return this.provider;
    }

    public void setProvider(Provider provider){
        this.provider = provider;
    }

    public Owner getOwner(){
        return this.owner;
    }

    public void setOwner(Owner owner){
        this.owner = owner;
    }

    public List<Invoice> getInvoices(){
        return new ArrayList<>(this.invoices);
    }

    public void setInvoices(List<Invoice> invoices){
        this.invoices = new ArrayList<>(invoices);
    }

    @Override
    public SmartHouse clone(){
        return new SmartHouse(this);
    }

    @Override
    public boolean equals(Object o) { // TODO Falta chechar owners e providers mas isso faz-se no fim
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SmartHouse that = (SmartHouse) o;
        return Objects.equals(address, that.address) && Objects.equals(devices, that.devices) && Objects.equals(divisions, that.divisions);
    }

    @Override
    public String toString() {
        return "SmartHouse{" +
                "address='" + address + '\'' +
                ", devices=" + devices +
                ", divisions=" + divisions +
                '}';
    }

    public boolean existsDevice(String id){
        return this.devices.containsKey(id);
    }

    public boolean existsDivision(String division){
        return this.divisions.containsKey(division);
    }

    public boolean existsDivisionOfDevice(String id){
        return this.divisions.containsKey(this.devices.get(id));
    }
    public boolean deviceInDivision(String id, String division){
        return Objects.equals(this.devices.get(id), division);
    }

    public void interactDiv(String division, Consumer<SmartDevice> smartDeviceConsumer){
        this.divisions.get(division).interact(smartDeviceConsumer);
    }

    public void interactHouse(Consumer<SmartDevice> divisionConsumer){
        for(Division division: this.divisions.values()){
            interactDiv(division.getName(), divisionConsumer);
        }
    }

    public void interactDevice(String id, Consumer<SmartDevice> smartDeviceConsumer){
        this.divisions.get(this.devices.get(id)).turnDevice(id, smartDeviceConsumer);
    }

    public Double totalConsumption(){
        double total = 0.0;

        for(Division division: this.divisions.values())
            total += division.divisonTotalConsumption();

        return total;
    }

    private Boolean isBetween(LocalDate t, LocalDate t1, LocalDate t2) {
        return t.compareTo(t1) >= 0 && t.compareTo(t2) <= 0;
    }
    public Double consumptionByPeriod() {
        return this.invoices.get(invoices.size() - 1).getConsumption();
    }

    public Double consumptionByPeriod(LocalDate start, LocalDate end){
        double cost = 0;
        for(Invoice invoice : invoices){
            if(isBetween(start, invoice.getStart(), invoice.getEnd())){
                cost += ((double) ChronoUnit.DAYS.between(start, invoice.getEnd()) / ChronoUnit.DAYS.between(invoice.getStart(), invoice.getEnd())) * invoice.getCost();
            }
            if(isBetween(invoice.getStart(), start, end) && isBetween(invoice.getEnd(), start, end)){
                cost += invoice.getCost();
            }
            if (isBetween(invoice.getStart(), start, end) && !isBetween(invoice.getEnd(), start, end)) {
                cost += ((double) ChronoUnit.DAYS.between(invoice.getStart(), end) / ChronoUnit.DAYS.between(invoice.getStart(), invoice.getEnd())) * invoice.getCost();
                break;
            }
        }

        /*Iterator<Invoice> iterator = invoices.iterator();
        Invoice invoice;
        for(invoice = iterator.next(); iterator.hasNext() && !isBetween(start, invoice.getStart(), invoice.getEnd()); invoice = iterator.next());
        if (isBetween(end, invoice.getStart(), invoice.getEnd())) {
            cost += ((double) ChronoUnit.DAYS.between(start, end) / ChronoUnit.DAYS.between(invoice.getStart(), invoice.getEnd())) * invoice.getCost();
        } else {
            cost += ((double) ChronoUnit.DAYS.between(start, invoice.getEnd()) / ChronoUnit.DAYS.between(invoice.getStart(), invoice.getEnd())) * invoice.getCost();
            for(invoice = iterator.next(); (iterator.hasNext() && isBetween(invoice.getStart(), start, end) && isBetween(invoice.getEnd(), start, end)); invoice = iterator.next())
                cost += invoice.getCost();
            cost += ((double) ChronoUnit.DAYS.between(invoice.getStart(), end) / ChronoUnit.DAYS.between(invoice.getStart(), invoice.getEnd())) * invoice.getCost();
        }*/
        return cost;
    }

    public Integer numberOfDevices(){
        return this.devices.keySet().size();
    }

    public void addDivision(Division division){
        this.divisions.put(division.getName(), division);
    }

    public void addDeviceToDivision(SmartDevice smartDevice, String division){
        //TODO cuidado com NullPointerExceptions por não existir uma divisão
        this.divisions.get(division).addDevice(smartDevice);
    }

    public Invoice invoiceEmission(LocalDate start, LocalDate end){
        //TODO é suposto a casa guardar a fatura que está a ser emitida para si
        return this.provider.invoiceEmission(this, start, end);
    }

    public void setDeviceOn(String id) {
        interactDevice(id, SmartDevice::turnOn);
    }

    public void setDeviceOff(String id) {
        interactDevice(id, SmartDevice::turnOff);
    }

    public void setDivisionOn(String division){
        interactDiv(division, SmartDevice::turnOn);
    }

    public void setDivisionOff(String division){
        interactDiv(division, SmartDevice::turnOff);
    }

    public void setHouseOn(){
        interactHouse(SmartDevice::turnOn);
    }

    public void setHouseOff(){
        interactHouse(SmartDevice::turnOff);
    }

}
