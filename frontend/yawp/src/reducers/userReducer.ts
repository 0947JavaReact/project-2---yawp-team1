import { bindActionCreators } from "redux";
import { ADD_USER, FETCH_USER, UPDATE_USER, LOGIN_USER } from "../actions/types";

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
    profile_pic: "",
    loggedIn: false
  },
};
export type Action = { type: string; payload: string };
export const userReducer = (state: any = initialState, action: Action) => {
  switch (action.type) {
    case ADD_USER:
      return {
        ...state,
        user: action.payload,
      };
    case FETCH_USER:
      console.log("in fetch" + JSON.stringify(action.payload));
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
    default:
      return state;
  }
};
