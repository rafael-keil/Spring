import createGlobalState from "react-create-global-state";

const userFromStorage = localStorage.getItem("user");

const SelectedUser = JSON.parse(userFromStorage) || {};

const [_useGlobalUser, UserProvider] = createGlobalState(SelectedUser);

const useGlobalUser = () => {
  const [user, _setGlobalUser] = _useGlobalUser();
  const setGlobalUser = (newUser) => {
    _setGlobalUser(newUser);
    localStorage.setItem("user", JSON.stringify(newUser));
  };
  return [user, setGlobalUser];
};

export { useGlobalUser, UserProvider };
