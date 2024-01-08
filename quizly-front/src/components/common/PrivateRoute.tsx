import { Route, Navigate } from "react-router-dom";

export interface PrivateRouteProps {
  Component: React.FunctionComponent;
  authenticated: boolean;
  children: any;
}
export default function PrivateRoute(props: PrivateRouteProps) {
  return (
    <Route
      element={
        props.authenticated ? (
          <props.Component {...props.children} />
        ) : (
          <Navigate replace={true} to="/login" />
        )
      }
    />
  );
}

// export default function PrivateRoute({
//   component: React.,
//   authenticated,
//   ...rest
// }) {
//   return (
//     <Route
//       {...rest}
//       render={(props) =>
//         authenticated ? (
//           <Component {...rest} {...props} />
//         ) : (
//           <Redirect
//             to={{
//               pathname: "/login",
//               state: { from: props.location },
//             }}
//           />
//         )
//       }
//     />
//   );
// }
