import React from 'react';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom';
import logo from './logo.svg';
import './App.css';
import {useDispatch, useSelector} from 'react-redux';
import { store } from './store';
import LoginPage from './views/LoginPage';
import RegisterPage from './views/RegisterPage';
import Navbar from './components/Navbar/Navbar';
import YawpPost from './components/YawpPost/YawpPost';
import img from './img/profile-picture-default.jpeg';


function App() {
  return (
    <Router>
      <Navbar />
      <Switch>
        <Route exact path="/"><LoginPage/></Route>
        <Route exact path="/forgotpass"></Route>
        <Route exact path="/register"><RegisterPage/></Route>
      </Switch>

      <YawpPost id={15} content="HOHYEAH" username="username" userPic={img} likes={90} />
    </Router>
  );
}

export default App;
