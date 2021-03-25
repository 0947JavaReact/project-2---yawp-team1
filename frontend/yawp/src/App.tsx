import React from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import logo from './logo.svg';
import './App.css';
import { useDispatch, useSelector } from 'react-redux';
import { store } from './store';
import LoginPage from './views/LoginPage';
import RegisterPage from './views/RegisterPage';
import Navbar from './components/Navbar/Navbar';
import YawpPost, { Yawp } from './components/YawpPost/YawpPost';
import img from './img/profile-picture-default.jpeg';
import { fetchYawps } from './actions/yawpActions';
import HomePage from './views/HomePage';

function App() {
  return (
    <Router>
      <Switch>
        <Route exact path="/"><LoginPage /></Route>
        <Route exact path="/forgotpass"></Route>
        <Route exact path="/register"><RegisterPage /></Route>
        <Route exact path="/home"><HomePage /></Route>
      </Switch>
    </Router>
  );
}

export default App;
