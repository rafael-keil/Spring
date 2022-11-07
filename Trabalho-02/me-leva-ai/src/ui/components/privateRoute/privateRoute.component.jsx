import { Redirect, Route } from "react-router";
import { useGlobalUser } from "../../../context";
import { ROUTES } from "../../../routes/routes.constants";

export function PrivateRoute({ path, children, passageiro }) {
  const [user] = useGlobalUser();

  if (user.passageiro !== passageiro) {
    return <Redirect to={ROUTES.HOME} />;
  }

  return (
    <Route path={path} exact>
      {children}
    </Route>
  );
}
