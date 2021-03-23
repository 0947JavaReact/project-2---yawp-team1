import React from 'react';
import logo from './logo.svg';
import './App.css';
import { Provider,connect } from 'react-redux';
import { store } from './store';
import { fetchUser } from './actions/userActions';




 function handleOnClick():void {
  fetchUser();
}

function App() {
  return (
    <Provider store={store}>
    <div className="App">
      <button name="button" onClick={handleOnClick}></button>
    </div>
    </Provider>
  );
}

export default connect(null, {fetchUser})(App);
