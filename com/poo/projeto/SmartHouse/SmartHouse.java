package com.poo.projeto.SmartHouse;

import com.poo.projeto.Invoice.Invoice;
import com.poo.projeto.Provider.Provider;
import com.poo.projeto.SmartHouse.Exceptions.DeviceDoesntExistException;
import com.poo.projeto.SmartHouse.Exceptions.DivisionDoesntExistException;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class SmartHouse implements Serializable {
    private String address;
    private Provider provider;

    private Double instalationCosts;//falta adicionar nos construtores
    private String name;
    private String nif;
    private List<Invoice> invoices;
    private Map<String, String> devices; // id -> string da divisão
    private Map<String, Division> divisions; // string da divisão -> Divisão (classe)

    /**
     * Constructor for objects of class CasaInteligente
     */
    public SmartHouse() {
        this.instalationCosts = 0.0;
        this.address = "";
        this.name = "";
        this.nif = "";
        this.devices = new HashMap<>();
        this.divisions = new HashMap<>();
        this.provider = new Provider();
        this.invoices = new ArrayList<>();
    }

    public SmartHouse(String address, Double instalationCosts, Map<String, String> devices, Map<String, Division> divisions, Provider provider, List<Invoice> invoices) {
        this.instalationCosts = instalationCosts;
        this.address = address;
        this.name = "";
        this.nif = "";
        this.devices = devices.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        this.divisions = divisions.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, div -> div.getValue().clone()));
        this.provider = provider;
        this.invoices = new ArrayList<>(invoices);
    }

    public SmartHouse(SmartHouse smartHouse){
        this.instalationCosts = smartHouse.getInstalationCosts();
        this.address = smartHouse.getAddress();
        this.devices = smartHouse.getDevices();
        this.divisions = smartHouse.getDivisions();
        this.provider = smartHouse.getProvider();
        this.invoices = smartHouse.getInvoices();
    }

    public SmartHouse(String address, String name, String nif){
        this.instalationCosts = 0.0;
        this.address = address;
        this.name = name;
        this.nif = nif;
        this.provider = null;
        this.invoices = new ArrayList<>();
        this.divisions = new HashMap<>();
        this.devices = new HashMap<>();
    }

    public SmartHouse(String address, String name, String nif, Provider provider){
        this.instalationCosts = 0.0;
        this.address = address;
        this.name = name;
        this.nif = nif;
        this.provider = provider;
        this.invoices = new ArrayList<>();
        this.divisions = new HashMap<>();
        this.devices = new HashMap<>();
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
        //Feito: não está a criar uma nova estrutura então de fora podem destruí-la
        this.devices = devices.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Map<String, Division> getDivisions(){
        return this.divisions.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, value -> value.getValue().clone()));
    }

    public void setDivisions(Map<String, Division> divisions){
        //Feito: não está a clonar as divisões
        //Feito: não está a criar uma nova estrutura então de fora podem destruí-la
        this.divisions = divisions.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e->e.getValue().clone()));
    }

    public Double getInstalationCosts() {
        Double tmp = this.instalationCosts;
        this.instalationCosts = 0.0;
        return tmp;
    }

    public void setInstalationCosts(Double instalationCosts) {
        this.instalationCosts = instalationCosts;
    }
    public Provider getProvider(){
        return this.provider;
    }

    public void setProvider(Provider provider){
        this.provider = provider;
    }


    public List<Invoice> getInvoices(){
        return new ArrayList<>(this.invoices);
    }

    public void setInvoices(List<Invoice> invoices){
        this.invoices = new ArrayList<>(invoices);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    @Override
    public SmartHouse clone(){
        return new SmartHouse(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SmartHouse that = (SmartHouse) o;
        return Objects.equals(address, that.address) && Objects.equals(devices, that.devices) && Objects.equals(divisions, that.divisions);
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

    private void interactDiv(String division, Consumer<SmartDevice> smartDeviceConsumer) throws DivisionDoesntExistException {
        Division div = this.divisions.get(division);
        if (div == null)
            throw new DivisionDoesntExistException("Division " + division + " doesn't exist in house " + this.name);
        div.interact(smartDeviceConsumer);
    }

    private void interactHouse(Consumer<SmartDevice> divisionConsumer) throws DivisionDoesntExistException {
        for(Division division: this.divisions.values()){
            interactDiv(division.getName(), divisionConsumer);
        }
    }

    private void interactDevice(String id, Consumer<SmartDevice> smartDeviceConsumer) throws DeviceDoesntExistException {
        String division = this.devices.get(id);
        if (division == null)
            throw new DeviceDoesntExistException("Device with id " + id + " doesn't exist");
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

    public Double consumptionByPeriod(LocalDate start, LocalDate end){
        //TODO testar isto num ficheiro à parte a ver se funciona
        double consumption = 0;
        for(Invoice invoice : invoices){
            if(isBetween(start, invoice.getStart(), invoice.getEnd())){
                consumption += ((double) ChronoUnit.DAYS.between(start, invoice.getEnd()) / ChronoUnit.DAYS.between(invoice.getStart(), invoice.getEnd())) * invoice.getConsumption();
            }
            if(isBetween(invoice.getStart(), start, end) && isBetween(invoice.getEnd(), start, end)){
                consumption += invoice.getConsumption();
            }
            if (isBetween(invoice.getStart(), start, end) && !isBetween(invoice.getEnd(), start, end)) {
                consumption += ((double) ChronoUnit.DAYS.between(invoice.getStart(), end) / ChronoUnit.DAYS.between(invoice.getStart(), invoice.getEnd())) * invoice.getConsumption();
                break;
            }
        }


        /*
        Iterator<Invoice> iterator = invoices.iterator();
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
        return consumption;
    }

    public Integer numberOfDevices(){
        return this.devices.keySet().size();
    }

    public void addDivision(Division division){
        this.divisions.put(division.getName(), division);
    }

    public void addDeviceToDivision(SmartDevice smartDevice, String division){//TODO o custo de instalaçao deve entrar aqui tipo aparecer na fatura quanto custou adicionar os devices
        if(divisions.containsKey(division)){
            //TODO cuidado com NullPointerExceptions por não existir uma divisão
            this.divisions.get(division).addDevice(smartDevice);
            this.instalationCosts += smartDevice.getInstallationCost();
        }

    }

    public Invoice invoiceEmission(LocalDate start, LocalDate end){
        //feito: é suposto a casa guardar a fatura que está a ser emitida para si
        Invoice newInvoice = this.provider.invoiceEmission(this, start, end);
        this.invoices.add(newInvoice);
        this.instalationCosts = 0.0;
        return newInvoice;
    }

    public void setDeviceOn(String id) throws DeviceDoesntExistException {
        interactDevice(id, SmartDevice::turnOn);
    }

    public void setDeviceOff(String id) throws DeviceDoesntExistException {
        interactDevice(id, SmartDevice::turnOff);
    }

    public void setDivisionOn(String division) throws DivisionDoesntExistException {
        interactDiv(division, SmartDevice::turnOn);
    }

    public void setDivisionOff(String division) throws DivisionDoesntExistException {
        interactDiv(division, SmartDevice::turnOff);
    }

    public void setHouseOn() throws DivisionDoesntExistException {
        interactHouse(SmartDevice::turnOn);
    }

    public void setHouseOff() throws DivisionDoesntExistException {
        interactHouse(SmartDevice::turnOff);
    }

    public boolean isSmartDeviceOn(String smartDevice) throws DeviceDoesntExistException {
        String division = this.devices.get(smartDevice);
        if (division == null)
            throw new DeviceDoesntExistException("Não existe device com o id " + smartDevice);
        return this.divisions.get(division).isSmartDeviceOn(smartDevice);
    }

    public void addSmartDevice(String divisionName, SmartDevice smartDevice){
        this.devices.put(smartDevice.getID(), divisionName);
        this.divisions.get(divisionName).addDevice(smartDevice);
        this.instalationCosts += smartDevice.getInstallationCost();
    }

    @Override
    public String toString() {
        return "Casa-> " +
                "código: " + this.address +
                ", fornecedor: " + this.provider.getName() +
                ", nome do dono: " + this.name +
                ", NIF do dono: " + this.nif +
                "\n\n    Faturas:" + "\n    "
                + this.invoices.stream().map(Invoice::toString).collect(Collectors.joining("\n    ")) +
                "\n\n    " + divisions.values().stream().map(Division::toString).collect(Collectors.joining("\n    "));
    }

    public void setBaseConsumption(String device, Double baseConsumption) throws DeviceDoesntExistException {
        interactDevice(device, d -> d.setBaseConsumption(baseConsumption));
    }
}
