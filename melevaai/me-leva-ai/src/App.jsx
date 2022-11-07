import "./App.css";
import { Switch, Route, Redirect } from "react-router-dom";
import {
  HomeScreen,
  CadastroScreen,
  CadastroVeiculoScreen,
  HomeMotorista,
  HomePassageiro,
} from "./ui/screens/";
import { ROUTES } from "./routes/routes.constants";
import { PrivateRoute } from "./ui/components";

function App() {
  return (
    <div className="App">
      <Switch>
        <Route path={ROUTES.HOME} exact>
          <HomeScreen />
        </Route>
        <Route path={ROUTES.CADASTRO} exact>
          <CadastroScreen />
        </Route>
        <PrivateRoute path={ROUTES.CADASTRO_VEICULO} passageiro={false}>
          <CadastroVeiculoScreen />
        </PrivateRoute>
        <PrivateRoute path={ROUTES.HOME_MOTORISTA} passageiro={false}>
          <HomeMotorista />
        </PrivateRoute>
        <PrivateRoute path={ROUTES.HOME_PASSAGEIRO} passageiro={true}>
          <HomePassageiro />
        </PrivateRoute>
        <Route path="/">
          <Redirect to={ROUTES.HOME} />
        </Route>
      </Switch>
    </div>
  );
}

export default App;
