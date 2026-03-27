using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Text;

namespace PCDoctor.Core
{
    class SystemMonitor
    {
        private PerformanceCounter cpuCounter;

        public SystemMonitor() 
        {
            // CPU counter (Winbdows only)
            cpuCounter = new PerformanceCounter("Processor", "% Processor Time", "_Total");

            _ = cpuCounter.NextValue();
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
            };
        }

        private (float totalRam, float availableRam) GetMemoryInfo()
        {
            try
            {
                return cpuCounter.NextValue();
            }
            catch (Exception)
            {

                throw;
            }
        }

        private float GetCpuUsage()
        {
            throw new NotImplementedException();
        }
    }
}
