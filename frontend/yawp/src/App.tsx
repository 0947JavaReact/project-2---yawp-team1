import * as React from 'react';
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
import EditProfilePage from './views/EditProfilePage';
import ResetPage from "./views/ResetPage"
import { setUser } from './actions/userActions';

function App() {

  const state = useSelector<any, any>((state) => state);

  React.useEffect(() => {
    console.log("In the use effect of App");
        if(state.user.user.id < 0){
          console.log("State needs updated");
            if(!localStorage.getItem('username')){
                return;
            }
            else{
                const user = {
                    username: localStorage.getItem("username"),
                    id: localStorage.getItem("id"),
                    bio: '',
                    profilePic: localStorage.getItem("profilePic"),
                    loggedIn: true,
                    loginAttempt: 'success'
                }
                getUser(user);
                console.log(state.user);
            }
        }
    }, [state.user.user.username]);

  const dispatch = useDispatch();

    const getUser = (user:any) => {
        dispatch(
            setUser(user)
        )
    };
  return (
    <div className="app">
    <Router>
      <Switch>
        <Route exact path="/"><LoginPage /></Route>
        <Route exact path="/forgotpass"><ResetPage></ResetPage></Route>
        <Route exact path="/register"><RegisterPage /></Route>
        <Route exact path="/home"><HomePage /></Route>
        <Route exact path="/user/:username" component={ProfilePage}></Route>
        <Route exact path="/followers/:username" component={FollowerPage}></Route>
        <Route exact path="/following/:username" component={FollowingPage}></Route>
        <Route exact path="/edit"><EditProfilePage/></Route>
        
      </Switch>
      
    </Router>
    </div>
  );
}

export default App;
