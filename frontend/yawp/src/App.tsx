import React from 'react';
import logo from './logo.svg';
import './App.css';
import {useDispatch, useSelector} from 'react-redux';
import { store } from './store';
import { fetchUser } from './actions/userActions';
import { User } from './reducers/userReducer';
import {LoginForm} from './components/LoginForm/LoginForm';
import { RegisterForm } from './components/RegisterForm/RegisterForm';
//import { TestButton } from './components/TestButton';

function App() {
  function handleOnClick():void {
    //console.log(storedUser.user);
    console.log(localStorage.getItem("id"));
  }
  const storedUser = useSelector<User, any>(
    
    (state:User) => state
  );

  const dispatch =  useDispatch();
  const getUser = ()=> {
    dispatch( 
      fetchUser()
    )
    console.log(`out side dispatch ${storedUser.user.user.username}`);
  }
  return (
    
    <div className="App">
      <h1 >{`Helloworld ${storedUser.user.user.username}`}</h1>
      <button onClick={handleOnClick}>Handle button</button>
      <button onClick={getUser}>Get Button</button>
      <RegisterForm />
    </div>
  );
}

export default App;
