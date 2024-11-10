import { Suspense } from "react";
import loadable from "@loadable/component";
import AuthLayout from "./layouts/AuthLayout";
import BasicLayout from "./layouts/BasicLayout";
import { CircularProgress } from "@mui/material";
import { BrowserRouter, Routes, Route } from "react-router-dom";

const Home = loadable(() => import("./pages/Home/index"));
const Login = loadable(() => import("./pages/Auth/Login/index"));

function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route element={<BasicLayout />}>
                    <Route
                        index
                        element={
                            <Suspense fallback={<CircularProgress />}>
                                <Home title="Trang chủ" />
                            </Suspense>
                        }
                    />
                </Route>
                <Route element={<AuthLayout />}>
                    <Route
                        path="/dang-nhap"
                        element={
                            <Suspense fallback={<CircularProgress />}>
                                <Login title="Đăng nhập" />
                            </Suspense>
                        }
                    />
                </Route>
            </Routes>
        </BrowserRouter>
    );
}

export default App;
