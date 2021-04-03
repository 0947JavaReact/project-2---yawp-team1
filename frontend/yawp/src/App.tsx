import * as React from 'react';
import { HashRouter as Router, Route, Switch } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { setUser } from './actions/userActions';
import LoginPage from './views/LoginPage';
import RegisterPage from './views/RegisterPage';
import HomePage from './views/HomePage';
import ProfilePage from './views/ProfilePage';
import FollowerPage from './views/FollowerPage';
import FollowingPage from './views/FollowingPage';
import EditProfilePage from './views/EditProfilePage';
import ResetPage from "./views/ResetPage"
import './App.css';
import SearchPage from './views/SearchPage';
import axios from 'axios';

function App() {

  const state = useSelector<any, any>((state) => state);

  React.useEffect(() => {
    if (state.user.user.id < 0) {
      if (!localStorage.getItem('id')) {
        return;
      }
      else {
          getUser(localStorage.getItem('id'));
      }
    }
  }, [state.user.user.username]);

  const dispatch = useDispatch();

  const getUser = async (id:any) => {

    let res = await axios.get(`http://ec2-3-101-86-38.us-west-1.compute.amazonaws.com:9025/users/userid/${id}`);

    let user = {
      username: res.data.username,
      id: res.data.userId,
      email: res.data.email,
      bio: res.data.bio,
      profilePic: res.data.picUrl,
      loggedIn: true
    }

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
          <Route exact path="/edit"><EditProfilePage /></Route>
          <Route exact path="/search/:keyword" component={SearchPage}></Route>
        </Switch>
      </Router>
    </div>
  );
}

export default App;
