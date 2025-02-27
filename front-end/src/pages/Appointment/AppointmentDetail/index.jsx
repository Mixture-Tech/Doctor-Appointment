import React from 'react';
import Detail from './components/Detail';
import Instruct from './components/Instruct';
import { ToastContainer } from 'react-toastify';

export default function AppointmentDetail() {
  return (
    <div className="w-full max-w-3xl mx-auto p-5 bg-white border border-gray-300 rounded-lg shadow-md font-sans text-center mt-4 mb-4">
      <ToastContainer style={{ marginTop: '50px' }} />
      <Detail />
      <Instruct />
    </div>
  );
}
