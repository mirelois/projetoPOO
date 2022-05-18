package Command;

import com.poo.projeto.Community.CommunityApp;
import com.poo.projeto.Provider.Exceptions.ProviderAlreadyExistsException;
import com.poo.projeto.Provider.Exceptions.ProviderDoesntExistException;
import com.poo.projeto.SmartHouse.Exceptions.AddressAlreadyExistsException;
import com.poo.projeto.SmartHouse.Exceptions.AddressDoesntExistException;
import com.poo.projeto.SmartHouse.Exceptions.DivisionAlreadyExistsException;

public interface Command {
    public abstract void execute(CommunityApp app) throws AddressAlreadyExistsException, ProviderDoesntExistException, ProviderAlreadyExistsException, DivisionAlreadyExistsException, AddressDoesntExistException;
}
