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
import HomePage from './views/HomePage';
import ProfilePage from './views/ProfilePage';
import FollowerPage from './views/FollowerPage';
import FollowingPage from './views/FollowingPage';

function App() {

  console.log({localStorage})
  return (
    <Router>
      <Switch>
        <Route exact path="/"><LoginPage /></Route>
        <Route exact path="/forgotpass"></Route>
        <Route exact path="/register"><RegisterPage /></Route>
        <Route exact path="/home"><HomePage /></Route>
        <Route exact path="/user/:username" component={ProfilePage}></Route>
        <Route exact path="/followers/:username" component={FollowerPage}></Route>
        <Route exact path="/following/:username" component={FollowingPage}></Route>
      </Switch>
      
    </Router>
  );
}

export default App;
