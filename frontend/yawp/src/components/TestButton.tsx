import * as React from "react";
import { Dispatch } from "redux";
import { useDispatch, useSelector } from "react-redux";
import { userReducer } from "../reducers/userReducer";
import { fetchUser } from "../actions/userActions";
/*
type User = {
  username: string;
  id: number;
  password: string;
  bio: string;
  profile_pic: string;
  email: string;
  fetchUser: () => any;
};
export const TestButton: React.FC<User> = ({ fetchUser }) => {
  const dispatch: Dispatch<any> = useDispatch();
  const User = useSelector((state) => state.User);
  const getUser = React.useCallback(() => dispatch(fetchUser()), [
    dispatch,
    fetchUser,
  ]);

  return (
    <div>
      <button onClick={() => dispatch(fetchUser())}></button>
    </div>
  );
};
*/