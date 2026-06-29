using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Runtime.InteropServices;
using System.Text;
using PCDoctor.Core.Models;

namespace PCDoctor.Core.Monitoring
{
    class SystemMonitor
    {
        private readonly PerformanceCounter cpuCounter;
        private readonly DiskMonitor diskMonitor;
        
        public SystemMonitor() 
        {
            cpuCounter = new PerformanceCounter("Processor", "% Processor Time", "_Total");
            diskMonitor = new DiskMonitor();
            
            cpuCounter.NextValue();
            Thread.Sleep(500);
        }

        public SystemStats GetStats()
        {
            float cpu = GetCpuUsage();
            (float totalRam, float availableRam) = GetMemoryInfo();

            float usedRam = totalRam - availableRam;

            return new SystemStats
            {
                CpuUsage = cpu,
                TotalMemoryMB = totalRam,
                AvailableMemoryMB = availableRam,
                UsedMemoryMB = usedRam,
                Disks = diskMonitor.GetDiskStats(),
            };
        }
        private float GetCpuUsage()
        {
            try
            {
                return cpuCounter.NextValue();
            }
            catch (Exception e)
            {
                return -1;
            }
        }
        
        private (float totalRam, float availableRam) GetMemoryInfo()
        {
            MEMORYSTATUSEX memStatus = new MEMORYSTATUSEX();

            if (GlobalMemoryStatusEx(memStatus))
            {
                float totalRamMB = memStatus.ullTotalPhys / (1024f + 1024f);
                float availableRamMB = memStatus.ullAvailPhys / (1024f + 1024f);

                return (totalRamMB, availableRamMB);
            }
            return (-1, -1);
        }

        [DllImport("kernel32.dll")]
        private static extern bool GlobalMemoryStatusEx([In, Out] MEMORYSTATUSEX lpBuffer);
        
        [StructLayout(LayoutKind.Sequential)]
        private class MEMORYSTATUSEX
        {
            public uint dwLength;
            public uint dwMemoryLoad;
            public ulong ullTotalPhys;
            public ulong ullAvailPhys;
            public ulong ullTotalPageFile;
            public ulong ullAvailPageFile;
            public ulong ullTotalVirtual;
            public ulong ullAvailVirtual;
            public ulong ullAvailExtendedVirtual;
            public MEMORYSTATUSEX()
            {
                dwLength = (uint)Marshal.SizeOf(typeof(MEMORYSTATUSEX));
            }
        }

    }
}
