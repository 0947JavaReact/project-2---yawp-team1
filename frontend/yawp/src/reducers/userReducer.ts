import userEvent from "@testing-library/user-event";
import { bindActionCreators } from "redux";
import { ADD_USER, SET_USER, UPDATE_USER, LOGIN_USER, LOGOUT_USER, ADD_FOLLOWING } from "../actions/types";

export interface User {
  username: string;
  id: number;
  bio: string;
  profile_pic: string;
}

const initialState = {
  user: {
    username: "",
    id: -1,
    bio: "",
    email: "",
    loggedInFollowing: [],
    profile_pic: "",
    loggedIn: false,
    loginAttempt: "none"
  }
};
export type Action = { type: string; payload: string };
export const userReducer = (state: any = initialState, action: Action) => {
  console.log('In user reducer');
  switch (action.type) {
    case ADD_USER:
      return {
        ...state,
        user: action.payload,
      };
    case SET_USER:
      return {
        ...state,
        user: action.payload,
      };
    case UPDATE_USER:
      return {
        ...state,
        user: action.payload,
      };
    case LOGIN_USER:
      return{
        ...state,
        user: action.payload
      }
    case LOGOUT_USER:
      return{
        ...state,
        user: action.payload
      }
    case ADD_FOLLOWING:
      let u = state.user;
      u.loggedInFollowing.push(action.payload);
      return{
        ...state,
        user: u
      }
    default:
      return state;
  }
};
