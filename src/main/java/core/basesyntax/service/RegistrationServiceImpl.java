package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.InvalidDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MAX_LOGIN_LENGTH = 6;
    private static final int MAX_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidDataException("User can't be null");
        }
        String userLogin = user.getLogin();
        if (storageDao.get(userLogin) != null || userLogin == null || userLogin.length() < MAX_LOGIN_LENGTH) {
            throw new InvalidDataException("Invalid login. Cannot register user");
        } else if (user.getPassword() == null || user.getPassword().length() < MAX_PASSWORD_LENGTH) {
            throw new InvalidDataException("Invalid password. Cannot register user");
        } else if (user.getAge() == null || user.getAge() < MIN_AGE) {
            throw new InvalidDataException("Invalid age. Cannot register user.");
        }
        storageDao.add(user);
        return user;
    }
}
