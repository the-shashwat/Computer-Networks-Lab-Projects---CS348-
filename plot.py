import matplotlib.pyplot as plt
import numpy as np

y,x = np.loadtxt('simulation1.txt', delimiter=',', unpack=True)
plt.plot(y,x, label='Graph')

plt.xlabel('loadfactor')
plt.ylabel('avg. delay')
plt.title('Average Delay vs LoadFactor')
plt.legend()
plt.show()

y,x = np.loadtxt('simulation2.txt', delimiter=',', unpack=True)
plt.plot(y,x, label='Graph')

plt.xlabel('loadfactor')
plt.ylabel('packet drop rate')
plt.title('Packet Drop Rate vs LoadFactor')
plt.legend()
plt.show()
